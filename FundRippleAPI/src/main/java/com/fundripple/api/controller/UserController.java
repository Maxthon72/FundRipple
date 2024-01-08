package com.fundripple.api.controller;
import com.fundripple.api.model.authentication.AuthenticationResponse;
import com.fundripple.api.model.authentication.RegisterRequest;
import com.fundripple.api.model.dto.read.UserReadModel;
import com.fundripple.api.model.dto.write.UserWriteModel;
import com.fundripple.api.service.AuthenticationService;
import com.fundripple.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping()
    public ResponseEntity<UserReadModel> GetUserByToken(@RequestHeader("Authorization") String header){
        return new ResponseEntity<>(userService.getUserDtoByToken(header), HttpStatus.OK);
    }

    @GetMapping("/role/{userName}")
    public ResponseEntity<String> getUserRole(@PathVariable String userName){
        return  new ResponseEntity<>(userService.getUserRole(userName),HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserReadModel> getUSerByName(
            @PathVariable String userName
    ){
        return ResponseEntity.ok(userService.getUserByName(userName));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request,
            @RequestHeader("Authorization") String header
    ){
        return ResponseEntity.ok(authenticationService.registerAdmin(request,header));
    }
    @PutMapping()
    public ResponseEntity<UserReadModel> updateUser(
            @RequestBody UserWriteModel userWriteModel,
            @RequestHeader("Authorization") String header
    ){
        return ResponseEntity.ok(userService.updateUser(userWriteModel,header));
    }
}
