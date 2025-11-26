//package com.example.service.config;
//
//import com.example.service.repository.RoleRepository;
//import com.example.service.repository.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class DataInitializer {
//    @Bean
//    CommandLineRunner initData(UserRepository userRepo,
//                               RoleRepository roleRepo,
//                               UserRoleRepository userRoleRepo,
//                               PasswordEncoder passwordEncoder) {
//        return args -> {
//
//            // Tạo role nếu chưa có
//            Role adminRole = roleRepo.findByName("ADMIN")
//                    .orElseGet(() -> {
//                        Role r = new Role();
//                        r.setName("ADMIN");
//                        return roleRepo.save(r);
//                    });
//
//            Role clientRole = roleRepo.findByName("CLIENT")
//                    .orElseGet(() -> {
//                        Role r = new Role();
//                        r.setName("CLIENT");
//                        return roleRepo.save(r);
//                    });
//
//            // Tạo user admin mặc định
//            if (!userRepo.existsByUsername("admin")) {
//
//                User admin = new User();
//                admin.setUsername("admin");
//                admin.setPassword(passwordEncoder.encode("123456")); // mật khẩu mặc định
//                admin.setEmail("admin@honeikery.com");
//                admin.setFullName("Administrator");
//
//                User savedAdmin = userRepo.save(admin);
//
//                // Gán role
//                UserRole adminUserRole = new UserRole();
//                adminUserRole.setUser(savedAdmin);
//                adminUserRole.setRole(adminRole);
//
//                userRoleRepo.save(adminUserRole);
//
//                System.out.println(">>> Đã tạo tài khoản admin mặc định: admin / 123456");
//            }
//        };
//    }
//}
