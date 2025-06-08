package org.etfbl.iprental.services;

import org.etfbl.iprental.models.DTO.EmployeeDTO;
import org.etfbl.iprental.models.EmployeeEntity;
import org.etfbl.iprental.models.ManufacturerEntity;
import org.etfbl.iprental.models.requests.EmployeeRequest;
import org.etfbl.iprental.repositories.EmployeeRepository;
import org.etfbl.iprental.utils.mappers.EmployeeMapper;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    /// The class uses BCrypt hashing algorithm
    private final BCryptPasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Integer id) {
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return employeeMapper.toDto(employee);
    }

    public EmployeeDTO getEmployeeByUsername(String username) {
        EmployeeEntity employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return employeeMapper.toDto(employee);
    }

    public EmployeeDTO addEmployee(EmployeeRequest requestDTO) {
        String hashedPassword = passwordEncoder.encode(requestDTO.getPassword());

        EmployeeEntity employee = employeeMapper.toEntity(requestDTO);
        employee.setPassword(hashedPassword);

        employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    public EmployeeDTO updateEmployee(Integer id, EmployeeDTO requestDTO) {
        EmployeeEntity existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

        existing.setFirstName(requestDTO.getFirstName());
        existing.setLastName(requestDTO.getLastName());
        existing.setUsername(requestDTO.getUsername());
        existing.setRole(requestDTO.getRole());

        return employeeMapper.toDto(employeeRepository.save(existing));

    }

    public Boolean verifyEmployeePassword(String username, String rawPassword) {
        EmployeeEntity employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return passwordEncoder.matches(rawPassword, employee.getPassword());
    }

    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }

    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }

}
