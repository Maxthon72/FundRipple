package com.fundripple.api.config;

import com.fundripple.api.mapper.UserMapper;
import com.fundripple.api.model.authentication.RegisterRequest;
import com.fundripple.api.model.dto.write.UserWriteModel;
import com.fundripple.api.model.entity.Authentication;
import com.fundripple.api.model.entity.Tag;
import com.fundripple.api.model.entity.User;
import com.fundripple.api.model.enums.Role;
import com.fundripple.api.repository.AuthenticationRepository;
import com.fundripple.api.repository.TagRepository;
import com.fundripple.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if the 'tags' table is empty
        if (tagRepository.count() == 0) {
            // Insert initial data
            List<String> tags = List.of("Fashion","Art","Books","Music","Food","Board Games"
                    ,"Video Games","Technology","Modern","Science-fiction","Fantasy","Health",
                    "Nature","Entertainment","Recreation","For children","Educational","Outdoor",
                    "Cinema");
            tags.forEach(value->{
                Tag tag = new Tag();
                tag.setTagName(value);
                tagRepository.save(tag);
            });

            System.out.println("Initial data inserted into the 'tags' table.");
        }
        if(userRepository.count()==0){
            RegisterRequest request = new RegisterRequest();
            request.setEmail("email@gmail.com");
            request.setPassword("password");
            request.setFirstName("first");
            request.setLastName("last");
            request.setUserName("user");
            Authentication authentication = new Authentication();
            authentication.setPassword(passwordEncoder.encode(request.getPassword()));
            authenticationRepository.save(authentication);
            User user = User.builder()
                    .userName(request.getUserName())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .authentication(authentication)
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
            Authentication authentication2 = new Authentication();
            authentication2.setPassword(passwordEncoder.encode("123"));
            authenticationRepository.save(authentication2);
            User user2 = User.builder()
                    .userName("admin")
                    .email("admin@gmail.com")
                    .firstName("admin")
                    .lastName("admin")
                    .authentication(authentication2)
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(user2);
        }
    }
}