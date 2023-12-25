package com.fundripple.api.repository;

import com.fundripple.api.model.entity.PostUnderProject;
import com.fundripple.api.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostUnderProjectRepository extends JpaRepository<PostUnderProject, Long> {
    List<PostUnderProject> getAllByProject(Project project);
}
