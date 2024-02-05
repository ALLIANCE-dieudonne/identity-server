package com.alliance.identityServer.service;

import com.alliance.identityServer.entity.UserCredentials;
import com.alliance.identityServer.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserCredentialRepository repository;
    private final JWTservice jwTservice;

    public String registerUser(UserCredentials credentials) {
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
        repository.save(credentials);
        Long userId = credentials.getId();
        return "User " + userId + " registered successfully";
    }

    public String generateToken(String username) {
        return jwTservice.generateToken(username);
    }

    public void validateToken(String token) {
        jwTservice.validateToken(token);
    }
}
