package com.fundripple.api.service;

import com.fundripple.api.error.ProjectException;
import com.fundripple.api.mapper.SubGoalMapper;
import com.fundripple.api.model.dto.write.SubGoalWriteModel;
import com.fundripple.api.model.entity.Project;
import com.fundripple.api.model.entity.SubGoal;
import com.fundripple.api.repository.ProjectRepository;
import com.fundripple.api.repository.SubGoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubGoalService {
    private final ProjectRepository projectRepository;
    private final SubGoalMapper subGoalMapper;
    private final SubGoalRepository subGoalRepository;
    private final CleanService cleanService;

    public SubGoalService(ProjectRepository projectRepository, SubGoalMapper subGoalMapper, SubGoalRepository subGoalRepository, CleanService cleanService) {
        this.projectRepository = projectRepository;
        this.subGoalMapper = subGoalMapper;
        this.subGoalRepository = subGoalRepository;
        this.cleanService = cleanService;
    }

    public List<SubGoal> addSubGoalsToProject(String projectName, List<SubGoalWriteModel> subGoalWriteModels) {
        try {
            Project project = projectRepository.findProjectByProjectName(projectName);
            for (SubGoalWriteModel subGoalWriteModel : subGoalWriteModels) {
                SubGoal subGoal = subGoalMapper.toEntity(subGoalWriteModel);
                subGoal.setProject(project);
                subGoalRepository.save(subGoal);
            }
            return subGoalRepository.getSubGoalsByProject(project);

        }catch (Exception e) {
            cleanService.clearProjectWhenAddingTagsOrDescription(projectName);
            throw new ProjectException("Failed to add project due to: " + e.getMessage());
        }
    }

    public List<SubGoal> getAllSubGoalsForProject(String projectName) {
        Project project = projectRepository.findProjectByProjectName(projectName);
        return subGoalRepository.getSubGoalsByProject(project);
    }
}
