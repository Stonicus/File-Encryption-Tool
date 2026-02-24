package com.example.file_encryption_tool.service;

import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Service
public class FileEncryptionService {

    private SecretKey generateKey(String password) throws Exception {
        byte[] keyBytes = Arrays.copyOf(
                password.getBytes(StandardCharsets.UTF_8), 16);
        return new SecretKeySpec(keyBytes, "AES");
    }

    public byte[] encryptFile(byte[] fileData, String password) throws Exception {
        SecretKey key = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        ByteArrayInputStream bis = new ByteArrayInputStream(fileData);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try (CipherOutputStream cos = new CipherOutputStream(bos, cipher)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                cos.write(buffer, 0, bytesRead);
            }
        }

        return bos.toByteArray();
    }

    public byte[] decryptFile(byte[] fileData, String password) throws Exception {
        SecretKey key = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        ByteArrayInputStream bis = new ByteArrayInputStream(fileData);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try (CipherInputStream cis = new CipherInputStream(bis, cipher)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = cis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
        }

        return bos.toByteArray();
    }
}
