package com.alliance.identityServer.controller;

import com.alliance.identityServer.dto.AuthRequest;
import com.alliance.identityServer.entity.UserCredentials;
import com.alliance.identityServer.service.AuthService;
import com.netflix.discovery.converters.Auto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v4/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredentials userCredentials) {
        return authService.registerUser(userCredentials);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return authService.generateToken(authRequest.getUsername());
        }else {
            throw new RuntimeException("Invalid Access");
        }
     }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "Token is valid";
    }
}
