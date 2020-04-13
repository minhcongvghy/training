package com.vsii.training.service;

import com.vsii.training.config.FileStorageProperties;
import com.vsii.training.exception.FileStorageException;
import com.vsii.training.exception.MyFileNotFoundException;
import com.vsii.training.model.DBFile;
import com.vsii.training.repository.DBFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Service
public class DBFileStorageService {

    private Path fileStorageLocation;

    @Autowired
    private DBFileRepository dbFileRepository;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    public void FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public DBFile storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Check if the file's name contains invalid characters
        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }

        DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getSize());
        dbFile.setUploadedTime(LocalDateTime.now());

        DBFile dbFile1 = dbFileRepository.save(dbFile);
        if (dbFile1 != null) {
            mailSenderService.sendSimpleEmail();
        }
        return dbFile1;
    }

    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }

}
