package com.vsii.training.service;

import com.vsii.training.exception.FileStorageException;
import com.vsii.training.exception.MyFileNotFoundException;
import com.vsii.training.model.DBFile;
import com.vsii.training.repository.DBFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
public class DBFileStorageService {

    @Autowired
    private DBFileRepository dbFileRepository;

    public DBFile storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Check if the file's name contains invalid characters
        if (fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }

        DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getSize());
        dbFile.setUploadedTime(LocalDateTime.now());

        return dbFileRepository.save(dbFile);
    }

    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }
}
