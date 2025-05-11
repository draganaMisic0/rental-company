package org.etfbl.iprental.models.requests;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadRequest {
    private Integer clientId;         // Nullable if it's for vehicle
    private String vehicleId;         // Nullable if it's for client
    private String type;              // "client" or "vehicle"
    private MultipartFile file;
}