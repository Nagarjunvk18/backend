package com.news.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.news.entity.User;
import com.news.repository.UserRepository;

public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController userController;
    
    @BeforeEach
    void setup() {
    	MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignUp() {
        User user = new User();
        user.setUserName("testuser");
        user.setPassword("password");

        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.OK).body("Signup Success");

        when(userRepository.getUserByUsername(user.getUserName())).thenReturn(null);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        ResponseEntity<String> response = userController.signUp(user);

        verify(userRepository).save(user);
        assertEquals(expectedResponse, response);
        
    }

    @Test
    public void testSignUpWithExistingUsername() {
        User user = new User();
        user.setUserName("existinguser");
        user.setPassword("password");

        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.CONFLICT).body("Username already present");

        when(userRepository.getUserByUsername(user.getUserName())).thenReturn(new User());

        ResponseEntity<String> response = userController.signUp(user);

        verify(userRepository, Mockito.never()).save(user);
    }

    @Test
    public void testLogin() {
        User user = new User();
        user.setUserName("testuser");
        user.setPassword("password");

        when(userRepository.getUserByUsername(user.getUserName())).thenReturn(user);

        User loggedInUser = userController.login(() -> user.getUserName());

        assertEquals(user, loggedInUser);
    }

    @Test
    public void testGetHello() {
        String expectedResponse = "Hello";

        String response = userController.getHello();

        assertEquals(expectedResponse, response);
    }

    @Test
    public void testGetAuthHello() {
        String expectedResponse = "Auth Hello";

        String response = userController.getAuthHello();

        assertEquals(expectedResponse, response);
    }

    @Test
    public void testGetPrivateRoleHello() {
        String expectedResponse = "Private Role Hello";

        String response = userController.getPrivateRoleHello();

        assertEquals(expectedResponse, response);
    }
}