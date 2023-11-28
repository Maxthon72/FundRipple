package com.fundripple.api.repository;

import com.fundripple.api.model.entity.Project;
import com.fundripple.api.model.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByStatus(ProjectStatus status);
}
