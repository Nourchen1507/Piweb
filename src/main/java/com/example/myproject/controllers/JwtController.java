package com.example.myproject.controllers;

import com.example.myproject.Service.JwtService;
import com.example.myproject.Service.UserService;
import com.example.myproject.entities.JwtRequest;
import com.example.myproject.entities.JwtResponse;
import com.example.myproject.entities.User;
import com.example.myproject.repositories.UserRepository;
import com.example.myproject.request.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
public class JwtController {
    private Logger logger = LoggerFactory.getLogger(JwtController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken( @RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);

    }
}