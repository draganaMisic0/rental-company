package org.etfbl.iprental.services;

import org.etfbl.iprental.models.ManufacturerEntity;
import org.etfbl.iprental.repositories.ManufacturerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public ManufacturerEntity addManufacturer(ManufacturerEntity manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    public List<ManufacturerEntity> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public ManufacturerEntity getManufacturerById(Integer id) {
        return manufacturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found with ID: " + id));
    }

    public ManufacturerEntity updateManufacturer(Integer id, ManufacturerEntity updatedManufacturer) {
        ManufacturerEntity existing = manufacturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found with ID: " + id));

        existing.setName(updatedManufacturer.getName());
        existing.setCountry(updatedManufacturer.getCountry());
        existing.setAddress(updatedManufacturer.getAddress());
        existing.setPhone(updatedManufacturer.getPhone());
        existing.setFax(updatedManufacturer.getFax());
        existing.setEmail(updatedManufacturer.getEmail());

        return manufacturerRepository.save(existing);
    }

    public void deleteManufacturer(Integer id) {
        manufacturerRepository.deleteById(id);
    }

    public void deleteAllManufacturers() {
        manufacturerRepository.deleteAll();
    }
}
