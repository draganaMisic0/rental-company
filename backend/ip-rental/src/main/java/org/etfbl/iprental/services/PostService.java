package org.etfbl.iprental.services;

import org.etfbl.iprental.models.DTO.PostDTO;
import org.etfbl.iprental.models.EmployeeEntity;
import org.etfbl.iprental.models.PostEntity;
import org.etfbl.iprental.repositories.EmployeeRepository;
import org.etfbl.iprental.repositories.PostRepository;
import org.etfbl.iprental.utils.mappers.PostMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final EmployeeRepository employeeRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, EmployeeRepository employeeRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.employeeRepository = employeeRepository;
        this.postMapper = postMapper;
    }

    public PostDTO addPost(PostDTO dto) {
        PostEntity entity = postMapper.toEntity(dto);

        EmployeeEntity employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + dto.getEmployeeId()));

        postMapper.setEmployee(entity, employee);

        PostEntity saved = postRepository.save(entity);
        return postMapper.toDto(saved);
    }

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    public PostDTO getPostById(Integer id) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + id));
        return postMapper.toDto(post);
    }

    public PostDTO updatePost(Integer id, PostDTO dto) {
        PostEntity existing = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + id));

        existing.setTitle(dto.getTitle());
        existing.setContent(dto.getContent());

        if (dto.getEmployeeId() != null && (existing.getEmployee() == null || !existing.getEmployee().getId().equals(dto.getEmployeeId()))) {
            EmployeeEntity employee = employeeRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + dto.getEmployeeId()));
            existing.setEmployee(employee);
        }

        PostEntity saved = postRepository.save(existing);
        return postMapper.toDto(saved);
    }

    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }

    public void deleteAllPosts() {
        postRepository.deleteAll();
    }
}
