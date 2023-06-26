package com.news.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.entity.Admin;
import com.news.service.AdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*", maxAge = 3006)
public class AdminController {

	@Autowired
	private AdminService adminService;

	// ADMIN Post API

	@PostMapping("/add")
	public ResponseEntity<String> postAdmin(@RequestBody Admin admin) {
		adminService.insertAdmin(admin);
		return ResponseEntity.status(HttpStatus.OK).body("Admin added in the DB");
	}

	// Get Api
	@GetMapping("/getall")
	public List<Admin> getAllAdmins() {
		List<Admin> list = adminService.getAllAdmins();
		return list;
	}

	@GetMapping("/{adminId}")
	public ResponseEntity<Object> getAdminById(@PathVariable("adminId") int adminId) {
		Optional<Admin> optional = adminService.getAdminById(adminId);
		if (!optional.isPresent())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID given");

		Admin admin = optional.get();
		return ResponseEntity.status(HttpStatus.OK).body(admin);
	}

	// Put ApI
	@PutMapping("/{id}")
	public ResponseEntity<String> updateAdminById(@PathVariable("id") int id, @RequestBody Admin admin) {
		adminService.updateAdminById(admin);
		return ResponseEntity.status(HttpStatus.OK).body("Admin is Updated");

	}

	// Delete Api

	@DeleteMapping("/api/admin/{id}")
	public ResponseEntity<String> deleteAdminById(@PathVariable("id") int id, @RequestBody Admin admin) {
		adminService.deleteAdminById(admin);
		return ResponseEntity.status(HttpStatus.OK).body("Admin is Deleted");
	}

}
