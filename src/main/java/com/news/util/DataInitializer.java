package com.news.util;

 

import javax.annotation.PostConstruct;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import com.news.entity.Admin;
import com.news.entity.User;
import com.news.repository.AdminRepository;
import com.news.repository.UserRepository;

 

@Component
public class DataInitializer {

 

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

 

    @Autowired
    public DataInitializer(AdminRepository adminRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

 

    @PostConstruct
    public void initData() {
        // Check if admin already exists
        if (adminRepository.count() == 0) {
            // Create default User
            User user = new User();
            user.setUserName("admin");
            user.setRole("ADMIN");
            user.setPassword(passwordEncoder.encode("admin@123")); // Assuming you are using password encoding

 

            // Create default Admin
            Admin admin = new Admin();
            admin.setName("Default Admin");
            admin.setMobile("9876543210");
            admin.setEmail("admin@example.com");
            admin.setUser(user);

 

            userRepository.save(user);
            adminRepository.save(admin);
        }
    }
}