package com.fundripple.api.controller;

import com.fundripple.api.model.dto.read.PostUnderProjectReadModel;
import com.fundripple.api.model.dto.read.PostUnderUserReadModel;
import com.fundripple.api.model.dto.read.ProjectReadModel;
import com.fundripple.api.model.dto.read.ProjectSLElement;
import com.fundripple.api.model.entity.User;
import com.fundripple.api.repository.UserRepository;
import com.fundripple.api.service.PostService;
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
    private final PostService postService;

    @Autowired
    public PublicController(UserRepository userRepository, ProjectService projectService, PostService postService) {
        this.userRepository = userRepository;
        this.projectService = projectService;
        this.postService = postService;
    }

    // Endpoint to create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectSLElement>> getAllProjectInSLE(
            @RequestParam(name = "status", defaultValue = "TO_VERIFY") String status
    ){
        return ResponseEntity.ok(projectService.getAllProjectsSLE(status));
    }

    @GetMapping("/postUnderProject/{projectName}")
    public ResponseEntity<List<PostUnderProjectReadModel>> getAllPostsForProject(
            @PathVariable String projectName
    ){
        return ResponseEntity.ok(postService.getPostsForProject(projectName));
    }

    @GetMapping("/postUnderUser/{userName}")
    public ResponseEntity<List<PostUnderUserReadModel>> getAllPostsForUser(
            @PathVariable String userName
    ){
        return ResponseEntity.ok(postService.getPostsForUser(userName));
    }

    @GetMapping("/project/{projectName}")
    public ResponseEntity<ProjectReadModel> getProject(
            @PathVariable String projectName
    ){
        return ResponseEntity.ok(projectService.getProjectByProjectName(projectName));
    }

//    // Endpoint to retrieve all users
//    @GetMapping("/users")
//
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> users = userRepository.findAll();
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }
}