package com.fundripple.api.service;

import com.fundripple.api.model.entity.Project;
import com.fundripple.api.model.entity.ProjectDescription;
import com.fundripple.api.model.entity.Tag;
import com.fundripple.api.repository.ProjectDescriptionRepository;
import com.fundripple.api.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CleanService {
    private final ProjectRepository projectRepository;
    private final ProjectDescriptionRepository projectDescriptionRepository;

    public CleanService(ProjectRepository projectRepository, ProjectDescriptionRepository projectDescriptionRepository) {
        this.projectRepository = projectRepository;
        this.projectDescriptionRepository = projectDescriptionRepository;
    }

    @Transactional
    public void clearProjectWhenAddingTagsOrDescription(String projectName){
        Project project = projectRepository.findProjectByProjectName(projectName);
        if (project == null) {
            throw new EntityNotFoundException("Project not found");
        }

        List<ProjectDescription> projectDescriptions = projectDescriptionRepository.findAllByProject(project);
        for(Tag tag : project.getTags()){
            tag.getProjects().remove(project);
        }
        projectDescriptionRepository.deleteAll(projectDescriptions);
        projectRepository.delete(project);
    }
}
