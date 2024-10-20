package com.urbanvoyage.ecom.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Utility {

    public static String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDateTime.now().format(formatter);
    }

    @Value("${file.storage.path}")
    private String basePath;

    public void createUserFolder(String userId) {
        File userFolder = new File(basePath + File.separator + userId);
        if (!userFolder.exists()) {
            boolean created = userFolder.mkdirs();
        }
    }

    public String uploadFile(MultipartFile file, String userId) throws IOException {
        // Create folder for the user if it doesn't exist

        // Define the file path
        String filePath = basePath + File.separator + userId + File.separator + file.getOriginalFilename();

        // Save the file
        File uploadFile = new File(filePath);
        FileOutputStream fos = new FileOutputStream(uploadFile);
        fos.write(file.getBytes());
        fos.close();

        // Return the path to be stored in MongoDB
        return filePath;
    }

    // Method to retrieve file path (from the MongoDB field)
    public String retrieveFilePath(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return filePath;
        }
        return null;  // Handle if file doesn't exist
    }

}

