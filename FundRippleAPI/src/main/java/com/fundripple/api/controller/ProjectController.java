package com.fundripple.api.controller;

import com.fundripple.api.error.ProjectException;
import com.fundripple.api.model.dto.read.ProjectReadModel;
import com.fundripple.api.model.dto.read.ProjectSLElement;
import com.fundripple.api.model.dto.write.ProjectDescriptionWriteModel;
import com.fundripple.api.model.dto.write.ProjectWriteModel;
import com.fundripple.api.model.dto.write.TagWriteModel;
import com.fundripple.api.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        } catch (ProjectException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<ProjectReadModel>>getAllProjects(){
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @PostMapping("/description/{projectName}")
    public ResponseEntity<?> setDescriptionToProject(
            @PathVariable String projectName,
            @RequestBody List<ProjectDescriptionWriteModel> projectDescriptionWriteModels){
        try {
            return ResponseEntity.ok(projectService.addDescriptionToProject(projectName, projectDescriptionWriteModels));
        }catch (ProjectException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/tag/{projectName}")
    public ResponseEntity<?> setTagsToProject(
            @PathVariable String projectName,
            @RequestBody List<TagWriteModel> tags){
        try {
            return ResponseEntity.ok(projectService.addTagsToProject(projectName, tags));
        }
        catch (ProjectException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/suspect/{projectName}/{userName}")
    public ResponseEntity<?> suspectProjectByUser(
            @PathVariable String projectName,
            @PathVariable String userName
    ){
        try{
            return ResponseEntity.ok(projectService.suspectProjectByUser(projectName,userName));
        }catch (ProjectException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/suspect/{projectName}/{userName}")
    public ResponseEntity<?> checkIfAlreadySuspects(
            @PathVariable String projectName,
            @PathVariable String userName
    ){
        try{
            return ResponseEntity.ok(projectService.suspectProjectByUser(projectName,userName));
        }catch (ProjectException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

}
