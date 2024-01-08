package com.fundripple.api.service;

import com.fundripple.api.mapper.UserMapper;
import com.fundripple.api.model.dto.read.UserReadModel;
import com.fundripple.api.model.dto.write.UserWriteModel;
import com.fundripple.api.model.entity.User;
import com.fundripple.api.model.enums.Role;
import com.fundripple.api.repository.UserRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserService(JwtService jwtService, UserMapper userMapper, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    //TO DO
    public UserReadModel getUserDtoByToken(String header){
        String jwtToken = header.substring(7);
        String userName = jwtService.extractUserFromToken(jwtToken);
        return userMapper.toDto(userRepository.findByUserName(userName).get());
    }

    public User getUserEntityByToken(String header){
        String jwtToken = header.substring(7);
        String userName = jwtService.extractUserFromToken(jwtToken);
        return userRepository.findByUserName(userName).get();
    }

    public String getUserRole(String userName) {
        return userRepository.findByUserName(userName).get().getRole().name();
    }

    public UserReadModel getUserByName(String userName){
        return userMapper.toDto(userRepository.findByUserName(userName).get());
    }

    public UserReadModel updateUser(UserWriteModel userWriteModel, String header) {
        User user = new User();
        if(Objects.equals(getUserEntityByToken(header).getUsername(), userWriteModel.getUserName())){
            user = userRepository.findByUserName(userWriteModel.getUserName()).get();
            user.setFirstName(userWriteModel.getFirstName());
            user.setLastName(userWriteModel.getLastName());
            user.setDescription(userWriteModel.getDescription());
            userRepository.save(user);
        }
        return userMapper.toDto(user);
    }
}
