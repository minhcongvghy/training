package com.vsii.training.payload;

import java.time.LocalDateTime;

public class UploadFileResponse {
    private String fileName;
    private String fileType;
    private long size;
    private LocalDateTime uploadedTime;

    public UploadFileResponse(String fileName, String fileType, long size, LocalDateTime uploadedTime) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.size = size;
        this.uploadedTime = uploadedTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public LocalDateTime getUploadedTime() {
        return uploadedTime;
    }

    public void setUploadedTime(LocalDateTime uploadedTime) {
        this.uploadedTime = uploadedTime;
    }
}
