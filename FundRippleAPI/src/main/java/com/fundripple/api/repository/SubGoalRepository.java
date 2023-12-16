package com.fundripple.api.repository;

import com.fundripple.api.model.entity.Project;
import com.fundripple.api.model.entity.SubGoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubGoalRepository extends JpaRepository<SubGoal, Long> {

    List<SubGoal> getSubGoalsByProject(Project project);
}
