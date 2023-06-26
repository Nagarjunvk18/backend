package com.news.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.news.entity.Admin;
import com.news.entity.User;
import com.news.service.AdminService;

public class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostAdmin() {
        Admin admin = new Admin(1L, "John Doe", "1234567890", "johndoe@example.com",new User());

        ResponseEntity<String> response = adminController.postAdmin(admin);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Admin added in the DB", response.getBody());

        verify(adminService, times(1)).insertAdmin(admin);
    }

    @Test
    public void testGetAllAdmins() {
        Admin admin1 = new Admin(1L, "John Doe", "1234567890", "johndoe@example.com",new User());
        Admin admin2 = new Admin(2L, "Jane Smith", "9876543210", "janesmith@example.com",new User());

        List<Admin> admins = Arrays.asList(admin1, admin2);
        when(adminService.getAllAdmins()).thenReturn(admins);

        List<Admin> response = adminController.getAllAdmins();

        assertEquals(admins, response);

        verify(adminService, times(1)).getAllAdmins();
    }

    @Test
    public void testGetAdminByIdValidId() {
        Admin admin = new Admin(1L, "John Doe", "1234567890", "johndoe@example.com",new User());

        when(adminService.getAdminById(1)).thenReturn(Optional.of(admin));

        ResponseEntity<Object> response = adminController.getAdminById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(admin, response.getBody());

        verify(adminService, times(1)).getAdminById(1);
    }

    @Test
    public void testGetAdminByIdInvalidId() {
        when(adminService.getAdminById(100)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = adminController.getAdminById(100);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid ID given", response.getBody());

        verify(adminService, times(1)).getAdminById(100);
    }

    @Test
    public void testUpdateAdminById() {
        Admin admin = new Admin(1L, "John Doe", "1234567890", "johndoe@example.com",new User());

        ResponseEntity<String> response = adminController.updateAdminById(1, admin);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Admin is Updated", response.getBody());

        verify(adminService, times(1)).updateAdminById(admin);
    }

    @Test
    public void testDeleteAdminById() {
        Admin admin = new Admin(1L, "John Doe", "1234567890", "johndoe@example.com",new User());

        ResponseEntity<String> response = adminController.deleteAdminById(1, admin);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Admin is Deleted", response.getBody());

        verify(adminService, times(1)).deleteAdminById(admin);
    }
}