package com.quarantinefriends.controller;

import com.quarantinefriends.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@CrossOrigin("http://localhost:4200")
public class FileController {
    private final FileService fileService;
    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        fileService.save(id, file);
        return ResponseEntity.status(HttpStatus.OK)
                .body("File uploaded successfully");
    }
}
