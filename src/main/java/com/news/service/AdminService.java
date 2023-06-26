package com.news.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.entity.Admin;
import com.news.repository.AdminRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;

	public void insertAdmin(Admin admin) {
		adminRepository.save(admin);
	}

	public List<Admin> getAllAdmins() {
		return adminRepository.findAll();
	}

	public Optional<Admin> getAdminById(int id) {
		Optional<Admin> optional = adminRepository.findById((long) id);
		return optional;
	}

	public void updateAdminById(Admin admin) {
		adminRepository.save(admin);
	}

	public void deleteAdminById(Admin admin) {
		adminRepository.delete(admin);
	}
}
