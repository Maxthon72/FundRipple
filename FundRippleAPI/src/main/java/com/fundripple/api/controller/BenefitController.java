package com.fundripple.api.controller;

import com.fundripple.api.mapper.BenefitMapper;
import com.fundripple.api.model.dto.read.BenefitReadModel;
import com.fundripple.api.model.dto.write.BenefitWriteModel;
import com.fundripple.api.model.dto.write.TagWriteModel;
import com.fundripple.api.service.BenefitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/benefit")
@RequiredArgsConstructor
public class BenefitController {
    private final BenefitService benefitService;
    private final BenefitMapper benefitMapper;
    @PostMapping("/forProject/{projectName}")
    ResponseEntity<List<BenefitReadModel>> addBenefitsToProject(@PathVariable String projectName,
                                                          @RequestBody List<BenefitWriteModel> benefitWriteModels){
        return ResponseEntity.ok(benefitMapper.mapToDto(benefitService.addBenefitsToProject(projectName,benefitWriteModels)));
    }

    @GetMapping("/forProject/{projectName}")
    ResponseEntity<List<BenefitReadModel>> getAllBenefitsForProject(@PathVariable String projectName){
        return ResponseEntity.ok(benefitMapper.mapToDto(benefitService.getAllBenefitsForProject(projectName)));
    }

    @GetMapping("/forUser/{userName}")
    ResponseEntity<List<BenefitReadModel>> getAllBenefitsForUser(@PathVariable String userName){
        return ResponseEntity.ok(benefitMapper.mapToDto(benefitService.getAllBenefitsForUser(userName)));
    }

}
