package org.etfbl.iprental.controllers;

import lombok.RequiredArgsConstructor;
import org.etfbl.iprental.services.ImagesService;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImagesController {

    private final ImagesService imagesService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
            @RequestParam("type") String type,
            @RequestParam("id") String id,
            @RequestParam("file") MultipartFile file) {
        String imageUrl = imagesService.uploadImage(type, id, file);
        return ResponseEntity.ok(imageUrl);
    }

    /// Type can either be: client OR vehicle. (Any other type will be treated as Unknown and will throw an Exception)
    @GetMapping("/{type}/{filename:.+}")
    public ResponseEntity<Resource> serveImage(
            @PathVariable String type,
            @PathVariable String filename) {

        Resource image = imagesService.loadImage(type, filename);

        MediaType contentType = MediaType.IMAGE_JPEG;
        try {       //  Try reading the file type, if not, then the default file type associated will be JPEG
            String detectedType = Files.probeContentType(image.getFile().toPath());
            if (detectedType != null) {
                contentType = MediaType.parseMediaType(detectedType);
            }
        } catch (IOException ignored) {}

        return ResponseEntity.ok()
                .contentType(contentType)
                .body(image);
    }
}