package com.example.dems.auth.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class AdminController {

    @GetMapping("/admin")
    public ResponseEntity<?> getAdmin(){
        Map<String,String> map = new HashMap<>();
        map.put("role","admin");
        map.put("Operation","GET Admin stuff");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?>getSomething(){
        Map<String,String> map = new HashMap<>();
        map.put("role","audience");
        map.put("message","testing");
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(){
        Map<String,String> map = new HashMap<>();
        map.put("role","user");
        map.put("Operation","GET User stuff");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    @GetMapping("/dashboard")
    public String adminDashboard(){
        return "Welcome to Admin Dashboard";
    }

}
