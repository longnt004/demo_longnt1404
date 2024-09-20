package com.fpoly.demo_longnt1404.config;

import com.fpoly.demo_longnt1404.model.Role;
import com.fpoly.demo_longnt1404.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initRole(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                List<Role> roles = new ArrayList<>();

                Role role1 = new Role();
                role1.setName("ADMIN");

                Role role2 = new Role();
                role2.setName("USER");

                roles.add(role1);
                roles.add(role2);

                roleRepository.saveAll(roles);
            }
        };
    }
}
