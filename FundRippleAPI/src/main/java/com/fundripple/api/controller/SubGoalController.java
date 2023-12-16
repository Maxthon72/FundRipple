package com.fundripple.api.controller;

import com.fundripple.api.mapper.SubGoalMapper;
import com.fundripple.api.model.dto.read.BenefitReadModel;
import com.fundripple.api.model.dto.read.SubGoalReadModel;
import com.fundripple.api.model.dto.write.BenefitWriteModel;
import com.fundripple.api.model.dto.write.SubGoalWriteModel;
import com.fundripple.api.service.SubGoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subGoal")
@RequiredArgsConstructor
public class SubGoalController {

    private final SubGoalMapper subGoalMapper;
    private final SubGoalService subGoalService;
    @PostMapping("/forProject/{projectName}")
    ResponseEntity<List<SubGoalReadModel>> addSubGoalsToProject(@PathVariable String projectName,
                                                                @RequestBody List<SubGoalWriteModel> subGoalWriteModels){
        return ResponseEntity.ok(subGoalMapper.mapToDto(subGoalService.addSubGoalsToProject(projectName,subGoalWriteModels)));
    }

    @GetMapping("/forProject/{projectName}")
    ResponseEntity<List<SubGoalReadModel>> getAllSubGoalsForProject(@PathVariable String projectName){
        return ResponseEntity.ok(subGoalMapper.mapToDto(subGoalService.getAllSubGoalsForProject(projectName)));
    }
}
