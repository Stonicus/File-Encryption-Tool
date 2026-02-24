package com.example.file_encryption_tool.model;

public class FileRequest {
    private String password;
    private String filePath;

    // Getters and setters
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}