package org.etfbl.iprental.controllers;

import org.etfbl.iprental.models.DTO.MalfunctionDTO;
import org.etfbl.iprental.models.MalfunctionEntity;
import org.etfbl.iprental.services.MalfunctionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/malfunctions")
public class MalfunctionController {

    private final MalfunctionService malfunctionService;

    public MalfunctionController(MalfunctionService malfunctionService) {
        this.malfunctionService = malfunctionService;
    }

    @PostMapping
    public ResponseEntity<MalfunctionDTO> addMalfunction(@RequestBody MalfunctionDTO dto) {
        return ResponseEntity.ok(malfunctionService.addMalfunction(dto));
    }

    @GetMapping
    public ResponseEntity<List<MalfunctionDTO>> getAllMalfunctions() {
        return ResponseEntity.ok(malfunctionService.getAllMalfunctions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MalfunctionDTO> getMalfunctionById(@PathVariable Integer id) {
        return ResponseEntity.ok(malfunctionService.getMalfunctionById(id));
    }

    @GetMapping("/vehicle/{id}")
    public ResponseEntity<List<MalfunctionDTO>> getMalfunctionByVehicleId(@PathVariable String id) {
        return ResponseEntity.ok(malfunctionService.getMalfunctionsByVehicleId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MalfunctionDTO> updateMalfunction(@PathVariable Integer id, @RequestBody MalfunctionDTO dto) {
        return ResponseEntity.ok(malfunctionService.updateMalfunction(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMalfunction(@PathVariable Integer id) {
        malfunctionService.deleteMalfunction(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllMalfunctions() {
        malfunctionService.deleteAllMalfunctions();
        return ResponseEntity.noContent().build();
    }
}
