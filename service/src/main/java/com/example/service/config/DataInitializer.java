package com.example.service.config;

import com.example.service.entity.Role;
import com.example.service.entity.User;
import com.example.service.repository.RoleRepository;
import com.example.service.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initAdminUser(UserRepository userRepository,
                                    RoleRepository roleRepository) {
        return args -> {
            // Tạo encoder để hash password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            // 1) Đảm bảo ROLE_ADMIN tồn tại
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName("ROLE_ADMIN");
                        return roleRepository.save(role);
                    });

            // 2) Kiểm tra xem admin đã tồn tại chưa
            String adminUsername = "admin";

            if (userRepository.findByUsername(adminUsername).isEmpty()) {

                User admin = new User();
                admin.setUsername(adminUsername);
                admin.setFullName("Honei-Kery Admin");
                admin.setPhone("0123456789");
                admin.setAddress("Hanoi");
                admin.setStatus("ACTIVE");
                admin.setPassword(encoder.encode("admin123")); // hash password
                admin.setRoles(adminRole);

                userRepository.save(admin);

                System.out.println("✅ Default admin created: " + adminUsername);
            } else {
                System.out.println("ℹ️ Admin already exists, skipping creation.");
            }
        };
    }

    @Bean
    CommandLineRunner initUser(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            // Tạo encoder để hash password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            // 1) Đảm bảo ROLE_USER tồn tại
            Role role = roleRepository.findByName("ROLE_USER")
                    .orElseGet(()->{
                        Role r = new Role();
                        r.setName("ROLE_USER");
                        return roleRepository.save(r);
                    });

            // 2) Kiểm tra xem user đã tồn tại chưa
            String userName = "user";

            if(userRepository.findByUsername(userName).isEmpty()) {
                User u = new User();
                u.setUsername(userName);
                u.setFullName("Honei-Kery User");
                u.setPhone("0123456789");
                u.setAddress("Hanoi");
                u.setStatus("ACTIVE");
                u.setPassword(encoder.encode("123"));
                u.setRoles(role);

                userRepository.save(u);

                System.out.println("✅ Default user created: " + userName);
            }
            else{
                System.out.println("️Default user already exists, skipping creation.");
            }

        };
    }
}
