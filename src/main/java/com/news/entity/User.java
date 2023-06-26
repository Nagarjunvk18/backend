package com.news.entity;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;

import javax.persistence.GenerationType;

import javax.persistence.Id;

import javax.persistence.Table;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;

import lombok.Data;

import lombok.NoArgsConstructor;

@Entity

@Data

@AllArgsConstructor

@NoArgsConstructor

@Table(name = "users")

public class User {

	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private long id;

	@NotBlank(message = "Username is required")

	private String userName;

	@NotBlank(message = "Role is required")

	private String role;

	@NotBlank(message = "Password is required")

	private String password;


}
