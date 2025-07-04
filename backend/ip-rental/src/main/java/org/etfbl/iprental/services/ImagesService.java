package org.etfbl.iprental.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.etfbl.iprental.models.ClientEntity;
import org.etfbl.iprental.models.VehicleEntity;
import org.etfbl.iprental.repositories.ClientRepository;
import org.etfbl.iprental.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;

@Service
@RequiredArgsConstructor
public class ImagesService {

    private final ClientRepository clientRepository;
    private final VehicleRepository vehicleRepository;

    @Value("${upload.client_images}")
    private String clientImagesDir;

    @Value("${upload.vehicle_images}")
    private String vehicleImagesDir;

    private Path getDirectory(String type) {
        return switch (type.toLowerCase()) {
            case "client" -> Paths.get(clientImagesDir);
            case "vehicle" -> Paths.get(vehicleImagesDir);
            default -> throw new IllegalArgumentException("Invalid image type: " + type);
        };
    }

    @Transactional
    public String uploadImage(String type, String id, MultipartFile file) {
        try {
            Path dirPath = getDirectory(type);
            Files.createDirectories(dirPath);

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = dirPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String imageUrl = "/api/images/" + type.toLowerCase() + "/" + filename;

            if ("client".equalsIgnoreCase(type)) {
                ClientEntity client = clientRepository.findById(Integer.parseInt(id))
                        .orElseThrow(() -> new RuntimeException("Client not found"));
                client.setAvatarUrl(filePath.toString());
                clientRepository.save(client);
            } else if ("vehicle".equalsIgnoreCase(type)) {
                VehicleEntity vehicle = vehicleRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Vehicle not found"));
                vehicle.setPhotoUrl(filePath.toString());
                vehicleRepository.save(vehicle);
            }

            return imageUrl;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }

    public Resource loadImage(String type, String filename) {
        try {
            Path filePath = getDirectory(type).resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Image not found or unreadable");
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid file path", e);
        }
    }


    public Resource loadVehicleImageById(String vehicleId) {
        VehicleEntity vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        String photoPath = vehicle.getPhotoUrl();
        if (photoPath == null || photoPath.isBlank()) {
            throw new RuntimeException("Vehicle has no photo");
        }

        try {
            Path imagePath = Paths.get(photoPath);
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Image file is not readable or does not exist");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid image path", e);
        }
    }

    @Transactional
    public void deleteVehicleImage(String id) {
        VehicleEntity vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        String photoPath = vehicle.getPhotoUrl();
        if (photoPath != null && !photoPath.isBlank()) {
            try {
                Path filePath = Paths.get(photoPath);
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete image file", e);
            }
        }

        vehicle.setPhotoUrl(null);
        vehicleRepository.save(vehicle);
    }
}

