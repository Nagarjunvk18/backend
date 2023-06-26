package com.news.service;
import com.news.entity.Admin;
import com.news.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    @Test
    public void testInsertAdmin() {
        Admin admin = new Admin();
        admin.setName("John");
        admin.setMobile("1234567890");
        admin.setEmail("john@example.com");

        adminService.insertAdmin(admin);

        verify(adminRepository).save(admin);
    }

    @Test
    public void testGetAllAdmins() {
        Admin admin1 = new Admin();
        admin1.setId(1L);
        admin1.setName("John");
        admin1.setMobile("1234567890");
        admin1.setEmail("john@example.com");

        Admin admin2 = new Admin();
        admin2.setId(2L);
        admin2.setName("Jane");
        admin2.setMobile("9876543210");
        admin2.setEmail("jane@example.com");

        List<Admin> admins = Arrays.asList(admin1, admin2);

        when(adminRepository.findAll()).thenReturn(admins);

        List<Admin> result = adminService.getAllAdmins();

        assertEquals(admins, result);
    }

    @Test
    public void testGetAdminById() {
        long adminId = 1L;
        Admin admin = new Admin();
        admin.setId(adminId);
        admin.setName("John");
        admin.setMobile("1234567890");
        admin.setEmail("john@example.com");

        Optional<Admin> optionalAdmin = Optional.of(admin);

        when(adminRepository.findById(adminId)).thenReturn(optionalAdmin);

        Optional<Admin> result = adminService.getAdminById((int) adminId);

        assertEquals(optionalAdmin, result);
    }

    @Test
    public void testUpdateAdminById() {
        Admin admin = new Admin();
        admin.setId(1L);
        admin.setName("John");
        admin.setMobile("1234567890");
        admin.setEmail("john@example.com");

        adminService.updateAdminById(admin);

        verify(adminRepository).save(admin);
    }

    @Test
    public void testDeleteAdminById() {
        Admin admin = new Admin();
        admin.setId(1L);
        admin.setName("John");
        admin.setMobile("1234567890");
        admin.setEmail("john@example.com");

        adminService.deleteAdminById(admin);

        verify(adminRepository).delete(admin);
    }
}
