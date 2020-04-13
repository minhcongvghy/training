package com.vsii.training;

import com.vsii.training.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class TrainingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainingApplication.class, args);
    }

}
