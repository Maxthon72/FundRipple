package com.fundripple.api.repository;

import com.fundripple.api.model.entity.Project;
import com.fundripple.api.model.entity.ProjectDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectDescriptionRepository extends JpaRepository<ProjectDescription, Long> {
    List<ProjectDescription> findAllByProject(Project project);
}
