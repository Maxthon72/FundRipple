package com.fundripple.api.service;

import com.fundripple.api.model.entity.Project;
import com.fundripple.api.model.entity.Suspicion;
import com.fundripple.api.repository.ProjectRepository;
import com.fundripple.api.repository.SuspicionRepository;
import org.springframework.stereotype.Service;

@Service
public class SuspectService {
    private final SuspicionRepository suspicionRepository;
    private final ProjectRepository projectRepository;

    public SuspectService(SuspicionRepository suspicionRepository, ProjectRepository projectRepository) {
        this.suspicionRepository = suspicionRepository;
        this.projectRepository = projectRepository;
    }

    public Boolean addSus(String projectName, String userName) {
        if(suspicionRepository.getSuspicionsByProjectAndUser(projectName, userName)==null){
            Suspicion suspicion = new Suspicion();
            suspicion.setUserName(userName);
            suspicion.setProjectName(projectName);
            suspicion.setId(1L);
            suspicionRepository.save(suspicion);
            Project project = projectRepository.findProjectByProjectName(projectName);
            project.setSuspicions(project.getSuspicions()+1);
            projectRepository.save(project);
            return true;
        }
        return false;
    }
}
