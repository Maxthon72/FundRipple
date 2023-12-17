package com.fundripple.api.service;

import com.fundripple.api.error.ProjectException;
import com.fundripple.api.mapper.BenefitMapper;
import com.fundripple.api.model.dto.write.BenefitWriteModel;
import com.fundripple.api.model.entity.Benefit;
import com.fundripple.api.model.entity.Project;
import com.fundripple.api.model.entity.User;
import com.fundripple.api.repository.BenefitRepository;
import com.fundripple.api.repository.ProjectRepository;
import com.fundripple.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BenefitService {

    private final ProjectRepository projectRepository;
    private final BenefitRepository benefitRepository;
    private final BenefitMapper benefitMapper;
    private final CleanService cleanService;

    public BenefitService(ProjectRepository projectRepository, BenefitRepository benefitRepository, BenefitMapper benefitMapper, CleanService cleanService) {
        this.projectRepository = projectRepository;
        this.benefitRepository = benefitRepository;
        this.benefitMapper = benefitMapper;
        this.cleanService = cleanService;
    }

    public List<Benefit> addBenefitsToProject(String projectName, List<BenefitWriteModel> benefitWriteModels) {
        try {
            Project project = projectRepository.findProjectByProjectName(projectName);
            for (BenefitWriteModel benefitWriteModel : benefitWriteModels) {
                Benefit benefit = benefitMapper.toEntity(benefitWriteModel);
                benefit.setProject(project);
                benefitRepository.save(benefit);
            }
            return benefitRepository.findAllByProject(project);
        }catch (Exception e) {
            cleanService.clearProjectWhenAddingTagsOrDescription(projectName);
            throw new ProjectException("Failed to add project due to: " + e.getMessage());
        }
    }

    public List<Benefit> getAllBenefitsForProject(String projectName) {
        Project project = projectRepository.findProjectByProjectName(projectName);
        return benefitRepository.findAllByProject(project);
    }

    public List<Benefit> getAllBenefitsForUser(String userName) {
        return benefitRepository.getListOfBenefitsForUser(userName);
    }
}
