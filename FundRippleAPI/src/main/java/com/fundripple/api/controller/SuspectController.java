package com.fundripple.api.controller;

import com.fundripple.api.model.dto.write.OnlyProjectName;
import com.fundripple.api.service.SuspectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sus")
@RequiredArgsConstructor
public class SuspectController {
    private final SuspectService suspectService;
    @PutMapping("/{userName}")
    ResponseEntity<Boolean> addSus(
            @RequestBody OnlyProjectName onlyProjectName,
            @PathVariable("userName") String userName
    ){
        return ResponseEntity.ok(suspectService.addSus(onlyProjectName,userName));
    }

    @GetMapping("/{userName}/{projectName}")
    ResponseEntity<Boolean> checkSus(
            @PathVariable("projectName") String projectName,
            @PathVariable("userName") String userName
    ){
        return ResponseEntity.ok(suspectService.checkIfSus(projectName,userName));
    }
}
