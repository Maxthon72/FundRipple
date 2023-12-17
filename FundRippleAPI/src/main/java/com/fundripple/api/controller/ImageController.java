package com.fundripple.api.controller;

import com.fundripple.api.model.entity.Project;
import com.fundripple.api.repository.ProjectRepository;
import com.fundripple.api.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ProjectRepository projectRepository;
    @PostMapping("/upload/description/{userName}/{projectName}/{imgName}")
    public ResponseEntity<String> uploadDescriptionImage(
            @RequestParam("image") MultipartFile imageFile,
            @PathVariable String userName,
            @PathVariable String projectName,
            @PathVariable String imgName) throws IOException {

        // Directory path updated to use the mapped volume in Docker
        //Path when not in docker
        String directoryPath = "C:" + File.separator + "Users" + File.separator + "Macie" + File.separator + "shared" + File.separator + userName + File.separator + projectName + File.separator + "description";
        //String directoryPath = "/app/resources" + File.separator + userName + File.separator + projectName + File.separator + "description";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Create directories if they don't exist
        }

        File file = new File(directory, imgName);
        if (!file.exists()) {
            file.createNewFile();
        } else {
            // Handle the case where file already exists (e.g., rename, throw an error, etc.)
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(imageFile.getBytes());
        } catch (IOException e) {
            // Handle exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving file");
        }

        // Return a generic success message or a URL to access the file
        return ResponseEntity.ok("File uploaded successfully");
    }

    @PostMapping("/upload/banner/{userName}/{projectName}/{imgName}")
    public ResponseEntity<String> uploadBannerImage(
            @RequestParam("image") MultipartFile imageFile,
            @PathVariable String userName,
            @PathVariable String projectName,
            @PathVariable String imgName) throws IOException {

        // Directory path updated to use the mapped volume in Docker
        //Path when not in docker
        String directoryPath = "C:" + File.separator + "Users" + File.separator + "Macie" + File.separator + "shared" + File.separator + userName + File.separator + projectName + File.separator + "banner";
        //String directoryPath = "/app/resources" + File.separator + userName + File.separator + projectName + File.separator + "banner";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Create directories if they don't exist
        }

        File file = new File(directory, imgName);
        if (!file.exists()) {
            file.createNewFile();
        } else {
            // Handle the case where file already exists (e.g., rename, throw an error, etc.)
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(imageFile.getBytes());
        } catch (IOException e) {
            // Handle exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving file");
        }
        Project project = projectRepository.findProjectByProjectName(projectName);
        project.setBannerURL(imgName);
        projectRepository.save(project);
        // Return a generic success message or a URL to access the file
        return ResponseEntity.ok("File uploaded successfully");
    }

}