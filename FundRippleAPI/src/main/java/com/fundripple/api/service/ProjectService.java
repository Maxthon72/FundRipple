package com.fundripple.api.service;

import com.fundripple.api.error.ProjectException;
import com.fundripple.api.mapper.ProjectMapper;
import com.fundripple.api.mapper.ProjectDescriptionMapper;
import com.fundripple.api.model.dto.read.ProjectReadModel;
import com.fundripple.api.model.dto.read.ProjectSLElement;
import com.fundripple.api.model.dto.write.EarlyCloseWriteModel;
import com.fundripple.api.model.dto.write.ProjectDescriptionWriteModel;
import com.fundripple.api.model.dto.write.ProjectWriteModel;
import com.fundripple.api.model.dto.write.TagWriteModel;
import com.fundripple.api.model.entity.*;
import com.fundripple.api.model.enums.ProjectDescriptionElementType;
import com.fundripple.api.model.enums.ProjectStatus;
import com.fundripple.api.model.enums.Role;
import com.fundripple.api.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private final SuspicionRepository suspicionRepository;
    private final JwtService jwtService;
    private final EarlyCloseRepository  earlyCloseRepository;


    public ProjectService(JwtService jwtService, UserService userService,
                          ProjectMapper projectMapper, ProjectRepository projectRepository,
                          ProjectDescriptionMapper projectDescriptionMapper,
                          ProjectDescriptionRepository projectDescriptionRepository,
                          TagRepository tagRepository, CleanService cleanService, SuspicionRepository suspicionRepository, JwtService jwtService1, EarlyCloseRepository earlyCloseRepository) {
        this.userService = userService;
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository;
        this.projectDescriptionMapper = projectDescriptionMapper;
        this.projectDescriptionRepository = projectDescriptionRepository;
        this.tagRepository = tagRepository;
        this.cleanService = cleanService;
        this.suspicionRepository = suspicionRepository;
        this.jwtService = jwtService1;
        this.earlyCloseRepository = earlyCloseRepository;
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


    public ProjectReadModel getProjectByProjectName(String projectName) {
        ProjectReadModel projectReadModel = projectMapper.toReadModel(projectRepository.findProjectByProjectName(projectName));
        projectReadModel.setDescription(projectDescriptionMapper.map(projectDescriptionRepository.findAllByProject(projectRepository.findProjectByProjectName(projectName))));
        return projectReadModel;
    }

    public List<ProjectSLElement> getProjectsWithSpecificStatus(String status,String header) {
        List<Project> projects = projectRepository.findAllByStatus(ProjectStatus.valueOf(status));
        return projectMapper.mapSLE(projects);
    }

    public Object setStatusOpen(String projectName,String header) {
        Project project = projectRepository.findProjectByProjectName(projectName);
        if(userService.getUserEntityByToken(header).getRole().name().equals("ADMIN")){
            project.setStatus(ProjectStatus.OPEN);
            projectRepository.save(project);
        }
        return projectMapper.toReadModel(project);
    }

    public Object setStatusEarlyClose(EarlyCloseWriteModel earlyCloseWriteModel,String header) {
        Project project = projectRepository.findProjectByProjectName(earlyCloseWriteModel.getProjectName());
        if(userService.getUserEntityByToken(header).getRole().name().equals("ADMIN")){
            project.setStatus(ProjectStatus.EARLY_CLOSE);
            EarlyClose earlyClose = new EarlyClose();
            earlyClose.setProject(project);
            earlyClose.setReason(earlyCloseWriteModel.getReason());
            project.setDateClosed(LocalDate.now());
            earlyCloseRepository.save(earlyClose);
            projectRepository.save(project);
        }
        return projectMapper.toReadModel(project);
    }

    public void autoCloseProjects() {

    }

    public List<ProjectSLElement> getAllOpenAndClosedProjectsSLE() {
        List<Project> projects = projectRepository.getOpenAndClosedProjects();
        return projectMapper.mapSLE(projects);
    }

    public List<ProjectSLElement> getAllProjectsToVerify(){
        List<Project> projects = projectRepository.getProjectsToVerify();
        return projectMapper.mapSLE(projects);
    }

    public List<ProjectSLElement> getAllProjectsForUserByHeader(String header) {
        List<Project> projects = projectRepository.getProjectsForUser(userService.getUserEntityByToken(header).getUsername());
        return projectMapper.mapSLE(projects);
    }

    public List<ProjectSLElement> getOpenAndClosedProjects(String header){
        User user = userService.getUserEntityByToken(header);
        List<Project> projects = new ArrayList<>();
        if(user.getRole()== Role.USER){
            projects = projectRepository.getOpenAndClosedProjectsForUser(user.getUsername());
        } else if (user.getRole()==Role.ADMIN) {
            projects = projectRepository.getOpenAndClosedProjects();
        }
        return projectMapper.mapSLE(projects);
    }

    public List<ProjectSLElement> getAllProjectsForUserByUserName(String userName) {
        List<Project> projects = projectRepository.getOpenAndClosedProjectsForUser(userName);
        return projectMapper.mapSLE(projects);
    }

    public List<ProjectSLElement> getOpenAndClosedProjectsForUser(String userName) {
        List<Project> projects = projectRepository.getOpenAndClosedProjectsForUser(userName);
        return projectMapper.mapSLE(projects);
    }

    public List<ProjectSLElement> getMostSuspectProjects() {
        List<Project> projects = projectRepository.getMostSuspectProjects();
        return projectMapper.mapSLE(projects);
    }
}
