package com.fundripple.api.controller;

import lombok.RequiredArgsConstructor;
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

    @PostMapping("/upload/description/{userName}/{projectName}/{imgName}")
    public ResponseEntity<String> uploadImage(
            @RequestParam("image") MultipartFile imageFile,
            @PathVariable String userName,
            @PathVariable String imgName,
            @PathVariable String projectName) throws IOException {
        String directory = "/app/resources/"+userName+"/"+projectName+"/description";
        String fileName = imgName+ ".jpg"; // You can use a dynamic name

        File file = new File(directory + fileName);
        file.createNewFile();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(imageFile.getBytes());
        }

        return ResponseEntity.ok(directory+"/"+fileName);
    }
}
