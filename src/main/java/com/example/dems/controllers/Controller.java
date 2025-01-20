package com.example.dems.controllers;

import com.example.dems.models.Data;
import com.example.dems.exception.NotFound;
import com.example.dems.services.service;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class Controller {

    private final com.example.dems.services.service service;

    @Autowired
    public Controller(com.example.dems.services.service service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<Data> createEmployee(@Valid @RequestBody Data data){
        return new ResponseEntity<>(service.createEmployee(data), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Data>> getEmployees(){
        return new ResponseEntity<>(service.getEmployees(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Data> getEmployeeById(@PathVariable Long id){
        return new ResponseEntity<>(service.getEmployeeById(id).orElseThrow(()->new NotFound("Employee Not Found")), HttpStatus.OK);
    }


    @GetMapping("/filter")
    public ResponseEntity<List<Data>> getEmployeesBySkillAndCertification(@RequestParam(required = false,defaultValue = "") String skill,
                                                                          @RequestParam(required = false,defaultValue = "") String certification){
        return new ResponseEntity<>(service.getEmployeesBySkillAndCertification(skill, certification), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Data> updateEmployee(@PathVariable Long id, @RequestBody Data data){
        return new ResponseEntity<>(service.updateEmployee(id, data), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        service.deleteEmployee(id);
        return new ResponseEntity<>("Done Deletion",HttpStatus.OK);
    }

    @GetMapping("/rank")
    public List<service.Record>getEmployeeRanks(){
        return service.getAllDataWithRanks();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Data>> getEmployeeByNameOrDepartment(@RequestParam(required = false,defaultValue = "") String name,
                                                                    @RequestParam(required = false,defaultValue = "") String department){
        return new ResponseEntity<>(service.getEmployeeByNameOrDepartment(name, department), HttpStatus.OK);
    }

}
