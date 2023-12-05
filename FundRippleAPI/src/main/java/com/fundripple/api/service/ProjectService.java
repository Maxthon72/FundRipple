package com.fundripple.api.service;

import com.fundripple.api.mapper.ProjectMapper;
import com.fundripple.api.mapper.ProjectDescriptionMapper;
import com.fundripple.api.mapper.UserMapper;
import com.fundripple.api.model.dto.read.ProjectReadModel;
import com.fundripple.api.model.dto.read.ProjectSLElement;
import com.fundripple.api.model.dto.write.PaymentWriteModel;
import com.fundripple.api.model.dto.write.ProjectDescriptionWriteModel;
import com.fundripple.api.model.dto.write.ProjectWriteModel;
import com.fundripple.api.model.entity.Payment;
import com.fundripple.api.model.entity.Project;
import com.fundripple.api.model.entity.ProjectDescription;
import com.fundripple.api.model.enums.ProjectDescriptionElementType;
import com.fundripple.api.model.enums.ProjectStatus;
import com.fundripple.api.repository.PaymentRepository;
import com.fundripple.api.repository.ProjectDescriptionRepository;
import com.fundripple.api.repository.ProjectRepository;
import com.fundripple.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final UserService userService;
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;
    private final ProjectDescriptionMapper projectDescriptionMapper;
    private final ProjectDescriptionRepository projectDescriptionRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public ProjectService(JwtService jwtService, UserService userService,
                          ProjectMapper projectMapper, ProjectRepository projectRepository,
                          ProjectDescriptionMapper projectDescriptionMapper,
                          ProjectDescriptionRepository projectDescriptionRepository,
                          UserMapper userMapper, UserRepository userRepository) {
        this.userService = userService;
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository;
        this.projectDescriptionMapper = projectDescriptionMapper;
        this.projectDescriptionRepository = projectDescriptionRepository;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
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
            Long projectId,
            List<ProjectDescriptionWriteModel> projectDescriptionWriteModels) {
        Project project = new Project();
        if(projectRepository.findById(projectId).isPresent()){
            project=projectRepository.findById(projectId).get();
        }
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
}
