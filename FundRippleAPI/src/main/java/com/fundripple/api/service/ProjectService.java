package com.fundripple.api.service;

import com.fundripple.api.mapper.ProjectMapper;
import com.fundripple.api.model.dto.read.ProjectReadModel;
import com.fundripple.api.model.dto.read.ProjectSLElement;
import com.fundripple.api.model.dto.write.ProjectWriteModel;
import com.fundripple.api.model.entity.Project;
import com.fundripple.api.model.enums.ProjectStatus;
import com.fundripple.api.repository.ProjectRepository;
import com.fundripple.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final UserService userService;
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(JwtService jwtService, UserService userService, ProjectMapper projectMapper, ProjectRepository projectRepository, UserRepository userRepository) {
        this.userService = userService;
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public ProjectReadModel addProject(ProjectWriteModel projectWriteModel,String header){
        Project project = projectMapper.toEntity(projectWriteModel);
        project.setResponsibleUser(userService.getUserEntityByToken(header));
        return projectMapper.toReadModel(projectRepository.save(project));
    }

    public List<ProjectSLElement> getAllProjectsSLE(String status){
        return projectMapper.mapSLE(
                projectRepository.findAllByStatus(
                        ProjectStatus.valueOf(status))
        );
    }
}
