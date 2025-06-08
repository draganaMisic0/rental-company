package org.etfbl.iprental.controllers;

import org.etfbl.iprental.models.DTO.PostDTO;
import org.etfbl.iprental.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDTO> addPost(@RequestBody PostDTO dto) {
        return ResponseEntity.ok(postService.addPost(dto));
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Integer id, @RequestBody PostDTO dto) {
        return ResponseEntity.ok(postService.updatePost(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllPosts() {
        postService.deleteAllPosts();
        return ResponseEntity.noContent().build();
    }
}
