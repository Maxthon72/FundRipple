package com.fundripple.api.controller;

import com.fundripple.api.model.dto.read.ProjectReadModel;
import com.fundripple.api.model.dto.write.PaymentWriteModel;
import com.fundripple.api.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    @PostMapping()
    public ResponseEntity<ProjectReadModel> addPayment(@RequestBody PaymentWriteModel paymentWriteModel,@RequestHeader("Authorization") String header){
        return ResponseEntity.ok(paymentService.addPayment(paymentWriteModel,header));
    }

    @GetMapping("/support/{projectName}")
    public ResponseEntity<?> getSupportOfProjectByUSer(
            @PathVariable String projectName,
            @RequestHeader("Authorization") String header
    ){
        return ResponseEntity.ok(paymentService.getSupportOgProjectByUser(projectName,header));
    }
}
