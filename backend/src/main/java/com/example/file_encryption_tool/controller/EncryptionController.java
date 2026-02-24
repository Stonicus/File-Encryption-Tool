package com.example.file_encryption_tool.controller;

import com.example.file_encryption_tool.service.FileEncryptionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;

@CrossOrigin(
        origins = "http://localhost:3000",
        exposedHeaders = HttpHeaders.CONTENT_DISPOSITION
)
@RestController
@RequestMapping("/api/encryption")
public class EncryptionController {

    private final FileEncryptionService fileEncryptionService;

    public EncryptionController(FileEncryptionService fileEncryptionService) {
        this.fileEncryptionService = fileEncryptionService;
    }

    @PostMapping("/encrypt")
    public ResponseEntity<byte[]> encryptFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("password") String password) {
        try {
            byte[] encryptedData = fileEncryptionService.encryptFile(file.getBytes(), password);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + file.getOriginalFilename() + ".enc")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(encryptedData);

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(("Encryption failed: " + e.getMessage()).getBytes());
        }
    }

    @PostMapping("/decrypt")
    public ResponseEntity<byte[]> decryptFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("password") String password) {
        try {
            byte[] decryptedData = fileEncryptionService.decryptFile(file.getBytes(), password);

            String originalName = file.getOriginalFilename().replace(".enc", ".dec");

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + originalName)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(decryptedData);

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(("Decryption failed: " + e.getMessage()).getBytes());
        }
    }
}