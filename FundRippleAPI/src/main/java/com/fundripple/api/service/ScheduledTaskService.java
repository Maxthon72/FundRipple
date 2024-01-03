package com.fundripple.api.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTaskService {

    private final ProjectService projectService;

    public ScheduledTaskService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Scheduled(fixedRate = 600000)
    public void performPeriodicTask() {

        checkProjectsToClose();
    }

    private void checkProjectsToClose(){
        projectService.autoCloseProjects();
    }
}
