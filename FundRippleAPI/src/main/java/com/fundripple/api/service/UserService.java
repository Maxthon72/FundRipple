package com.fundripple.api.service;

import com.fundripple.api.mapper.UserMapper;
import com.fundripple.api.model.dto.read.UserReadModel;
import com.fundripple.api.repository.UserRepository;
import org.springframework.stereotype.Service;

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
    public UserReadModel getUserByToken(String header){
        String jwtToken = header.substring(7);
        String userName = jwtService.extractUserFromToken(jwtToken);
        return userMapper.toReadModel(userRepository.findByUserName(userName).get());
    }
}
