package com.example.dems.auth.controllers;

import com.example.dems.auth.dtos.JwtResponse;
import com.example.dems.auth.dtos.LoginRequest;
import com.example.dems.auth.dtos.RegisterRequest;
import com.example.dems.auth.models.Role;
import com.example.dems.auth.models.RoleName;
import com.example.dems.auth.models.User;
import com.example.dems.auth.repository.RoleRepository;
import com.example.dems.auth.repository.UserRepository;
import com.example.dems.auth.utils.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class AuthController {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder encoder;
    private JwtUtils jwtUtils;

    @Autowired
    public AuthController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request){

        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("Username is already Taken");
        }

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(()->new RuntimeException("Role not Found"));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRoles(roles);

        userRepository.save(user);
        return ResponseEntity.ok("User Registered Successfully");

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request){

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(()->new RuntimeException("User not Found"));

        if(!encoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid Credentials");
        }

        String token = jwtUtils.generateToken(user.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));

    }

}
