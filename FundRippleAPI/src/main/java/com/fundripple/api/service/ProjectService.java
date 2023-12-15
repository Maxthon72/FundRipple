package com.fundripple.api.service;

import com.fundripple.api.error.ProjectException;
import com.fundripple.api.mapper.ProjectMapper;
import com.fundripple.api.mapper.ProjectDescriptionMapper;
import com.fundripple.api.model.dto.read.ProjectReadModel;
import com.fundripple.api.model.dto.read.ProjectSLElement;
import com.fundripple.api.model.dto.write.ProjectDescriptionWriteModel;
import com.fundripple.api.model.dto.write.ProjectWriteModel;
import com.fundripple.api.model.dto.write.TagWriteModel;
import com.fundripple.api.model.entity.Project;
import com.fundripple.api.model.entity.ProjectDescription;
import com.fundripple.api.model.entity.Tag;
import com.fundripple.api.model.enums.ProjectDescriptionElementType;
import com.fundripple.api.model.enums.ProjectStatus;
import com.fundripple.api.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    private final UserService userService;
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;
    private final ProjectDescriptionMapper projectDescriptionMapper;
    private final ProjectDescriptionRepository projectDescriptionRepository;
    private final TagRepository tagRepository;
    private final CleanService cleanService;


    public ProjectService(JwtService jwtService, UserService userService,
                          ProjectMapper projectMapper, ProjectRepository projectRepository,
                          ProjectDescriptionMapper projectDescriptionMapper,
                          ProjectDescriptionRepository projectDescriptionRepository,
                          TagRepository tagRepository, CleanService cleanService) {
        this.userService = userService;
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository;
        this.projectDescriptionMapper = projectDescriptionMapper;
        this.projectDescriptionRepository = projectDescriptionRepository;
        this.tagRepository = tagRepository;
        this.cleanService = cleanService;
    }

    public List<ProjectReadModel> getAllProjects(){
        List<Project> projects = projectRepository.findAll();
        List<ProjectReadModel> projectReadModels = new ArrayList<>();
        for(Project project:projects){
            ProjectReadModel projectReadModel = projectMapper.toReadModel(project);
            projectReadModel.setDescription(projectDescriptionMapper.map(projectDescriptionRepository.findAllByProject(project)));
            projectReadModels.add(projectReadModel);
        }

        return projectReadModels;
    }
    public ProjectReadModel addProject(ProjectWriteModel projectWriteModel, String header) {
        try {
            Project project = projectMapper.toEntity(projectWriteModel);
            project.setResponsibleUser(userService.getUserEntityByToken(header));
            return projectMapper.toReadModel(projectRepository.save(project));
        } catch (Exception e) {
            throw new ProjectException("Failed to add project due to: " + e.getMessage());
        }
    }

    public List<ProjectSLElement> getAllProjectsSLE(String status){
        List<Project> projects = projectRepository.findAllByStatus(
                ProjectStatus.valueOf(status));
        return projectMapper.mapSLE(projects);
    }

    public ProjectReadModel addDescriptionToProject(
            String projectName,
            List<ProjectDescriptionWriteModel> projectDescriptionWriteModels) {
        try{
        Project project = new Project();

        project=projectRepository.findProjectByProjectName(projectName);
        for(ProjectDescriptionWriteModel projectDescriptionWriteModel:projectDescriptionWriteModels){
            ProjectDescription projectDescription = projectDescriptionMapper.toEntity(projectDescriptionWriteModel);
            projectDescription.setProject(project);
            projectDescription.setType(ProjectDescriptionElementType.valueOf(projectDescriptionWriteModel.getType()));
            projectDescriptionRepository.save(projectDescription);
        }
        ProjectReadModel projectReadModel = projectMapper.toReadModel(project);
        projectReadModel.setDescription(projectDescriptionMapper.map(projectDescriptionRepository.findAllByProject(project)));
        return projectReadModel;
    } catch (Exception e) {
            cleanService.clearProjectWhenAddingTagsOrDescription(projectName);
        throw new ProjectException("Failed to add project due to: " + e.getMessage());
    }
    }

    public ProjectReadModel addTagsToProject(
            String projectName,
            List<TagWriteModel> tags
    ){
        try{
        Project project = new Project();
        project=projectRepository.findProjectByProjectName(projectName);
        for(TagWriteModel tagWriteModel:tags){
            Tag tag = tagRepository.findTagByTagName(tagWriteModel.getTagName());
            tag.getProjects().add(project);
            project.getTags().add(tag);
            tagRepository.save(tag);
        }
        projectRepository.save(project);
        ProjectReadModel projectReadModel = projectMapper.toReadModel(project);
        projectReadModel.setDescription(projectDescriptionMapper.map(projectDescriptionRepository.findAllByProject(project)));
        return projectReadModel;
        } catch (Exception e) {
            cleanService.clearProjectWhenAddingTagsOrDescription(projectName);
            throw new ProjectException("Failed to add project due to: " + e.getMessage());
        }
    }

}
