package com.fundripple.api.controller;
import com.fundripple.api.model.dto.read.UserReadModel;
import com.fundripple.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping()
    public ResponseEntity<UserReadModel> GetUserByToken(@RequestHeader("Authorization") String header){
        return new ResponseEntity<>(userService.getUserDtoByToken(header), HttpStatus.OK);
    }
}
