package com.example.dems.auth.utils;

import com.example.dems.auth.models.Role;
import com.example.dems.auth.models.RoleName;
import com.example.dems.auth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(RoleName.values())
                .forEach(roleName -> {
                    if(roleRepository.findByName(roleName).isEmpty()){
                        Role role = new Role();
                        role.setName(roleName);
                        roleRepository.save(role);
                    }
                });
    }
}
