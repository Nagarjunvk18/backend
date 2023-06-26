package com.news.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.builders.WebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.news.service.UserService;

@Configuration

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired

	private UserService userService;

	@Override

	@Bean

	public AuthenticationManager authenticationManagerBean() throws Exception {

		return super.authenticationManagerBean();

	}

	@Override

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.authenticationProvider(getAuthProvider());

	}

	@Override

	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()

				.antMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

				// Unrestricted access

				.antMatchers(HttpMethod.GET, "/api/user/hello").permitAll()

				.antMatchers(HttpMethod.GET, "/api/newspapers/getall").permitAll()

				// Restricted access for authenticated users

				.antMatchers(HttpMethod.GET, "/api/user/login").authenticated()

				.antMatchers(HttpMethod.GET, "/api/user/private/hello").authenticated()

				.antMatchers(HttpMethod.GET, "/api/newspapers/**").authenticated()

				// Admin access

				.antMatchers("/**").hasAuthority("ADMIN")
				.antMatchers("/**").hasAuthority("ADVERTISER")
				

				.and().httpBasic()

				.and().csrf().disable();

	}

	private DaoAuthenticationProvider getAuthProvider() {

		DaoAuthenticationProvider dao = new DaoAuthenticationProvider();

		dao.setPasswordEncoder(getPasswordEncoder());

		dao.setUserDetailsService(userService);

		return dao;

	}

	@Bean

	PasswordEncoder getPasswordEncoder() {

		PasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder;

	}

	@Override

	public void configure(WebSecurity web) throws Exception {

		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
				"/configuration/security", "/swagger-ui.html", "/webjars/**");

	}

}
