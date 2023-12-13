package com.fundripple.api.service;

import com.fundripple.api.mapper.ProjectMapper;
import com.fundripple.api.mapper.ProjectDescriptionMapper;
import com.fundripple.api.mapper.UserMapper;
import com.fundripple.api.model.dto.read.ProjectReadModel;
import com.fundripple.api.model.dto.read.ProjectSLElement;
import com.fundripple.api.model.dto.write.PaymentWriteModel;
import com.fundripple.api.model.dto.write.ProjectDescriptionWriteModel;
import com.fundripple.api.model.dto.write.ProjectWriteModel;
import com.fundripple.api.model.dto.write.TagWriteModel;
import com.fundripple.api.model.entity.Payment;
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


    public ProjectService(JwtService jwtService, UserService userService,
                          ProjectMapper projectMapper, ProjectRepository projectRepository,
                          ProjectDescriptionMapper projectDescriptionMapper,
                          ProjectDescriptionRepository projectDescriptionRepository,
                          TagRepository tagRepository) {
        this.userService = userService;
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository;
        this.projectDescriptionMapper = projectDescriptionMapper;
        this.projectDescriptionRepository = projectDescriptionRepository;
        this.tagRepository = tagRepository;
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
    public ProjectReadModel addProject(ProjectWriteModel projectWriteModel,String header){
        Project project = projectMapper.toEntity(projectWriteModel);
        project.setResponsibleUser(userService.getUserEntityByToken(header));
        return projectMapper.toReadModel(projectRepository.save(project));
    }

    public List<ProjectSLElement> getAllProjectsSLE(String status){
        List<Project> projects = projectRepository.findAllByStatus(
                ProjectStatus.valueOf(status));
        return projectMapper.mapSLE(projects);
    }

    public ProjectReadModel addDescriptionToProject(
            String projectName,
            List<ProjectDescriptionWriteModel> projectDescriptionWriteModels) {
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
    }

    public ProjectReadModel addTagsToProject(
            String projectName,
            List<TagWriteModel> tags
    ){
        Project project = new Project();
        project=projectRepository.findProjectByProjectName(projectName);
        for(TagWriteModel tagWriteModel:tags){
            Tag tag = new Tag();
            tag = tagRepository.findTagByTagName(tag.getTagName());
            tag.getProjects().add(project);
            project.getTags().add(tag);
            tagRepository.save(tag);
        }
        projectRepository.save(project);
        ProjectReadModel projectReadModel = projectMapper.toReadModel(project);
        projectReadModel.setDescription(projectDescriptionMapper.map(projectDescriptionRepository.findAllByProject(project)));
        return projectReadModel;
    }

    public ProjectReadModel getspecificProjects(String projectName) {
        Project project = projectRepository.findProjectByProjectName(projectName);
        ProjectReadModel projectReadModel = projectMapper.toReadModel(project);
        projectReadModel.setDescription(projectDescriptionMapper.map(projectDescriptionRepository.findAllByProject(project)));
        return projectReadModel;
    }
}
