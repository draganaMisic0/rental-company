package org.etfbl.iprental.controllers;

import org.etfbl.iprental.services.CSVService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/csv")
public class CSVController {

    private final CSVService csvService;

    public CSVController(CSVService csvService) {
        this.csvService = csvService;
    }

    @PostMapping("/bulk-upload")
    public ResponseEntity<Map<String, String>> uploadVehiclesFromCsv(@RequestParam("file") MultipartFile file) {
        try {
            csvService.bulkUpload(file);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Vehicles uploaded successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("ERROR HAPPENED");
            System.out.println(e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}
