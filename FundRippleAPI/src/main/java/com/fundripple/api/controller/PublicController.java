package com.fundripple.api.controller;

import com.fundripple.api.model.dto.read.ProjectSLElement;
import com.fundripple.api.model.entity.User;
import com.fundripple.api.repository.UserRepository;
import com.fundripple.api.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final UserRepository userRepository;
    private final ProjectService projectService;

    @Autowired
    public PublicController(UserRepository userRepository, ProjectService projectService) {
        this.userRepository = userRepository;
        this.projectService = projectService;
    }

    // Endpoint to create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectSLElement>> getAllProjectInSLE(
            @RequestParam(name = "status", defaultValue = "OPEN") String status
    ){
        return ResponseEntity.ok(projectService.getAllProjectsSLE(status));
    }

//    // Endpoint to retrieve all users
//    @GetMapping("/users")
//
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> users = userRepository.findAll();
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }
}