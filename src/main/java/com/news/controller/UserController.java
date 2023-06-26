package com.news.controller;

import java.security.Principal;
import java.util.Random;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.entity.User;
import com.news.repository.UserRepository;
import com.news.service.EmailService;
import com.news.util.LoggerUtil;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody User user) {

        User userDB = userRepository.getUserByUsername(user.getUserName());
        if (userDB != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("Signup Success");
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody User user) {
        User existingUser = userRepository.getUserByUsername(user.getUserName());
        if (existingUser != null) {
            if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid current password");
            }

            String newPassword = generateNewPassword();
            existingUser.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(existingUser);
            return ResponseEntity.status(HttpStatus.OK).body("Password changed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
/*
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody User user) {
        User existingUser = userRepository.getUserByUsername(user.getUserName());
        if (existingUser != null) {
            String newPassword = generateNewPassword();
            existingUser.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(existingUser);

            String recipientEmail = existingUser.getEmail();
            String subject = "Password Reset";
            String message = "Your new password is: " + newPassword;
            try {
                emailService.sendEmail(recipientEmail, subject, message);
                LoggerUtil.logInfo("what happened");
                return ResponseEntity.status(HttpStatus.OK).body("Password reset successfully");
            } catch (MessagingException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
*/
    @GetMapping("/login")
    public User login(Principal principal) {
        User user = userRepository.getUserByUsername(principal.getName());
        return user;
    }

    @GetMapping("/hello")
    public String getHello() {
        return "Hello";
    }

    @GetMapping("/private/hello")
    public String getAuthHello() {
        return "Auth Hello";
    }

    @GetMapping("private/role/hello")
    public String getPrivateRoleHello() {
        return "Private Role Hello";
    }
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";

    private static final int PASSWORD_LENGTH = 8; // Desired password length

    public static String generateNewPassword() {
        StringBuilder password = new StringBuilder();

        Random random = new Random();
        int uppercaseCount = 2; // Number of uppercase letters in the password
        int lowercaseCount = 4; // Number of lowercase letters in the password
        int digitCount = 2; // Number of digits in the password

        // Generate uppercase letters
        for (int i = 0; i < uppercaseCount; i++) {
            char uppercaseLetter = UPPERCASE_LETTERS.charAt(random.nextInt(UPPERCASE_LETTERS.length()));
            password.append(uppercaseLetter);
        }

        // Generate lowercase letters
        for (int i = 0; i < lowercaseCount; i++) {
            char lowercaseLetter = LOWERCASE_LETTERS.charAt(random.nextInt(LOWERCASE_LETTERS.length()));
            password.append(lowercaseLetter);
        }

        // Generate digits
        for (int i = 0; i < digitCount; i++) {
            char digit = DIGITS.charAt(random.nextInt(DIGITS.length()));
            password.append(digit);
        }

        // Shuffle the characters in the password string
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int randomIndex = random.nextInt(PASSWORD_LENGTH);
            char temp = password.charAt(i);
            password.setCharAt(i, password.charAt(randomIndex));
            password.setCharAt(randomIndex, temp);
        }

        return password.toString();
    }

}
