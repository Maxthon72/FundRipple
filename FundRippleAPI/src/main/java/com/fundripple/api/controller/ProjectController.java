package com.fundripple.api.controller;

import com.fundripple.api.error.CustomException;
import com.fundripple.api.model.authentication.AuthenticationResponse;
import com.fundripple.api.model.authentication.RegisterRequest;
import com.fundripple.api.model.dto.read.ProjectReadModel;
import com.fundripple.api.model.dto.read.ProjectSLElement;
import com.fundripple.api.model.dto.write.PaymentWriteModel;
import com.fundripple.api.model.dto.write.ProjectDescriptionWriteModel;
import com.fundripple.api.model.dto.write.ProjectWriteModel;
import com.fundripple.api.model.dto.write.TagWriteModel;
import com.fundripple.api.model.entity.Project;
import com.fundripple.api.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.form.TagWriter;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    @PostMapping("")
    public ResponseEntity<?> addProject(
            @RequestBody ProjectWriteModel projectWriteModel,
            @RequestHeader("Authorization") String header
    ) {
        try {
            return ResponseEntity.ok(projectService.addProject(projectWriteModel, header));
        } catch (CustomException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<ProjectReadModel>>getAllProjects(){
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{projectName}")
    public ResponseEntity<ProjectReadModel>getSpecificProject(
            @PathVariable String projectName
    ){
        return ResponseEntity.ok(projectService.getspecificProjects(projectName));
    }

    @PostMapping("/description/{projectName}")
    public ResponseEntity<ProjectReadModel> setDescriptionToProject(
            @PathVariable String projectName,
            @RequestBody List<ProjectDescriptionWriteModel> projectDescriptionWriteModels){
        return ResponseEntity.ok(projectService.addDescriptionToProject(projectName,projectDescriptionWriteModels));
    }

    @PostMapping("/tag/{projectName}")
    public ResponseEntity<ProjectReadModel> setTagsToProject(
            @PathVariable String projectName,
            @RequestBody List<TagWriteModel> tags){
        return ResponseEntity.ok(projectService.addTagsToProject(projectName,tags));
    }

}
