package com.fundripple.api.controller;

import com.fundripple.api.model.authentication.AuthenticationResponse;
import com.fundripple.api.model.authentication.RegisterRequest;
import com.fundripple.api.model.dto.read.ProjectReadModel;
import com.fundripple.api.model.dto.read.ProjectSLElement;
import com.fundripple.api.model.dto.write.ProjectWriteModel;
import com.fundripple.api.model.entity.Project;
import com.fundripple.api.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    @PostMapping()
    public ResponseEntity<ProjectReadModel> addProject(
            @RequestBody ProjectWriteModel projectWriteModel,
            @RequestHeader("Authorization") String header
    ){
        return ResponseEntity.ok(projectService.addProject(projectWriteModel,header));
    }

    @GetMapping("/SLE")
    public ResponseEntity<List<ProjectSLElement>> getAllProjectInSLE(
            @RequestParam(name = "status", defaultValue = "OPEN") String status
    ){
        return ResponseEntity.ok(projectService.getAllProjectsSLE(status));
    }
}
