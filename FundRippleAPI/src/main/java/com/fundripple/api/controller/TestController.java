package com.fundripple.api.controller;


import com.fundripple.api.model.authentication.AuthenticationRequest;
import com.fundripple.api.model.authentication.RegisterRequest;
import com.fundripple.api.model.authentication.AuthenticationResponse;
import com.fundripple.api.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/user")
    public ResponseEntity<Boolean> sayHi(){
        return ResponseEntity.ok(true);
    }
}
