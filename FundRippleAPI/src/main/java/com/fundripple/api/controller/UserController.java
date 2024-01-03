package com.fundripple.api.controller;
import com.fundripple.api.model.dto.read.UserReadModel;
import com.fundripple.api.model.dto.write.UserWriteModel;
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

    @PutMapping()
    public ResponseEntity<UserReadModel> updateUser(
            @RequestBody UserWriteModel userWriteModel,
            @RequestHeader("Authorization") String header
    ){
        return ResponseEntity.ok(userService.updateUser(userWriteModel,header));
    }
}
