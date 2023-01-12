package com.spring.demo.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.demo.dto.JwtResponse;
import com.spring.demo.dto.LoginRequest;
import com.spring.demo.dto.MessageResponse;
import com.spring.demo.dto.SignupRequest;
import com.spring.demo.entity.Role;
import com.spring.demo.entity.User;
import com.spring.demo.security.service.UserDetailsImpl;
import com.spring.demo.repo.UserRepo;
import com.spring.demo.repo.RoleRepo;
import com.spring.demo.utils.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepo userRepo;

	@Autowired
	RoleRepo roleRepo;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtil jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser( @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		


		return ResponseEntity.ok(new JwtResponse(jwt));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser( @RequestBody SignupRequest signUpRequest) {
		if (userRepo.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepo.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		long roleId = signUpRequest.getRoleId();
		

		
			Role role = roleRepo.findById(roleId).get();
		
		
		user.setRole(role);
		userRepo.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
