package org.etfbl.iprental.controllers;

import org.etfbl.iprental.models.DTO.EmployeeDTO;
import org.etfbl.iprental.models.ManufacturerEntity;
import org.etfbl.iprental.models.requests.EmployeeRequest;
import org.etfbl.iprental.models.requests.LoginRequest;
import org.etfbl.iprental.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<EmployeeDTO> getEmployeeByUsername(@PathVariable String username) {
       EmployeeDTO returnedObject =  null;
       try{
        returnedObject = employeeService.getEmployeeByUsername(username);
       }
       catch(Exception e){
           return ResponseEntity.notFound().build();
       }

       return ResponseEntity.ok(returnedObject);
    }

    @PostMapping("/register")
    public ResponseEntity<EmployeeDTO> registerEmployee(@RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(employeeService.addEmployee(request));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        boolean success = false;
        try{
            success = employeeService.verifyEmployeePassword(loginRequest.getUsername(), loginRequest.getPassword());
        }
        catch(Exception ex)
        {
            return ResponseEntity.status(401).body("Invalid credentials");
        }


        if (success) {
            EmployeeDTO foundEmployee = employeeService.getEmployeeByUsername(loginRequest.getUsername());
            return ResponseEntity.ok(foundEmployee);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employee, @PathVariable Integer id) {
        if(id == null || id == -1){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/username/{username}")
    public ResponseEntity<?> deleteEmployeeByUsername(@PathVariable String username) {

        EmployeeDTO returnedObject = null;

        try{
            returnedObject = employeeService.getEmployeeByUsername(username);
            employeeService.deleteEmployee(returnedObject.getId());
            return ResponseEntity.noContent().build();
        }
        catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllEmployees() {
        employeeService.deleteAllEmployees();
        return ResponseEntity.noContent().build();
    }
}

