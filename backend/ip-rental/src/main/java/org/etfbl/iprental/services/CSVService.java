package org.etfbl.iprental.services;

import org.etfbl.iprental.models.DTO.BicycleDTO;
import org.etfbl.iprental.models.DTO.CarDTO;
import org.etfbl.iprental.models.DTO.ScooterDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class CSVService {

    private final CarService carService;
    private final BicycleService bicycleService;
    private final ScooterService scooterService;

    public CSVService(CarService carService, BicycleService bicycleService, ScooterService scooterService) {
        this.carService = carService;
        this.bicycleService = bicycleService;
        this.scooterService = scooterService;
    }

    public void bulkUpload(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            for (String line : reader.lines().collect(Collectors.toList())) {
                String[] parts = line.split("#");
                String type = parts[0].toLowerCase();
                System.out.println(line);
                switch (type) {
                    case "car":
                        CarDTO carDTO = new CarDTO();
                        carDTO.setModel(parts[1]);
                        carDTO.setPurchasePrice(new BigDecimal(parts[2]));
                        carDTO.setPhotoUrl(parts[3]);
                        carDTO.setStatus(parts[4]);
                        carDTO.setRentalPrice(new BigDecimal(parts[5]));
                        carDTO.setManufacturerId(Integer.parseInt(parts[6]));
                        carDTO.setPurchaseDate(LocalDate.parse(parts[7]));
                        carDTO.setDescription(parts[8]);
                        carService.addCar(carDTO);
                        break;

                    case "bicycle":
                        BicycleDTO bicycleDTO = new BicycleDTO();
                        bicycleDTO.setModel(parts[1]);
                        bicycleDTO.setPurchasePrice(new BigDecimal(parts[2]));
                        bicycleDTO.setPhotoUrl(parts[3]);
                        bicycleDTO.setStatus(parts[4]);
                        bicycleDTO.setRentalPrice(new BigDecimal(parts[5]));
                        bicycleDTO.setManufacturerId(Integer.parseInt(parts[6]));
                        bicycleDTO.setRange(Integer.parseInt(parts[7]));
                        bicycleService.addBicycle(bicycleDTO);
                        break;

                    case "scooter":
                        ScooterDTO scooterDTO = new ScooterDTO();
                        scooterDTO.setModel(parts[1]);
                        scooterDTO.setPurchasePrice(new BigDecimal(parts[2]));
                        scooterDTO.setPhotoUrl(parts[3]);
                        scooterDTO.setStatus(parts[4]);
                        scooterDTO.setRentalPrice(new BigDecimal(parts[5]));
                        scooterDTO.setManufacturerId(Integer.parseInt(parts[6]));
                        scooterDTO.setMaxSpeed(Integer.parseInt(parts[7]));
                        scooterService.addScooter(scooterDTO);
                        break;

                    default:
                        throw new IllegalArgumentException("Unsupported type: " + type);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to process CSV file", e);
        }
    }
}
