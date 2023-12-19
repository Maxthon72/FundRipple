package com.fundripple.api.controller;

import com.fundripple.api.service.SuspectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sus")
@RequiredArgsConstructor
public class SuspectController {
    private final SuspectService suspectService;
    @PutMapping("/{projectName}/{userName}")
    ResponseEntity<Boolean> addSus(
            @PathVariable("projectName") String projectName,
            @PathVariable("userName") String userName
    ){
        return ResponseEntity.ok(suspectService.addSus(projectName,userName));
    }
}
