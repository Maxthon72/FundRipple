package com.fundripple.api.repository;

import com.fundripple.api.model.entity.Project;
import com.fundripple.api.model.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByStatus(ProjectStatus status);
    Project findProjectByProjectName(String projectName);

    @Query(value = "SELECT * FROM projects where status = 'OPEN' or status='CLOSED'",nativeQuery = true)
    List<Project> getOpenAndClosedProjects();

    @Query(value = "SELECT * FROM projects where status = 'OPEN'",nativeQuery = true)
    List<Project> getOpenProjects();

    @Query(value = "SELECT * FROM projects where status = 'TO_VERIFY'",nativeQuery = true)
    List<Project> getProjectsToVerify();

    @Query(value = "SELECT * FROM projects where responsible_user = (SELECT id from users where user_name=?1)",nativeQuery = true)
    List<Project> getProjectsForUser(String userName);

    @Query(value = "SELECT * FROM projects where responsible_user = (SELECT id from users where user_name=?1) and (status = 'OPEN' or status='CLOSED')",nativeQuery = true)
    List<Project> getOpenAndClosedProjectsForUser(String userName);

}
