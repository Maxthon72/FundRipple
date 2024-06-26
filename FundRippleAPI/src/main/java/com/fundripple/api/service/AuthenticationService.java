package com.fundripple.api.service;

import com.fundripple.api.model.authentication.AuthenticationRequest;
import com.fundripple.api.model.authentication.AuthenticationResponse;
import com.fundripple.api.model.authentication.RegisterRequest;
import com.fundripple.api.model.entity.Authentication;
import com.fundripple.api.model.entity.User;
import com.fundripple.api.model.enums.Role;
import com.fundripple.api.repository.AuthenticationRepository;
import com.fundripple.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationRepository authenticationRepository;
    public AuthenticationResponse register(RegisterRequest request) {
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
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUserName(request.getUserName())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerAdmin(RegisterRequest request, String header) {
        Authentication authentication = new Authentication();
        authentication.setPassword(passwordEncoder.encode(request.getPassword()));
        authenticationRepository.save(authentication);
        User user = User.builder()
                .userName(request.getUserName())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .authentication(authentication)
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
