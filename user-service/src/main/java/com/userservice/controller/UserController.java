package com.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.dto.UserRequestDTO;
import com.userservice.dto.UserResponseDTO;
import com.userservice.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	
	@GetMapping("{email}")
	public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email){
		return new ResponseEntity<UserResponseDTO>(userService.getUserByEmail(email),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequest){
		return new ResponseEntity<UserResponseDTO>(userService.registerUser(userRequest),HttpStatus.CREATED);
	}
	
	@PostMapping("login")
	public ResponseEntity<UserResponseDTO> loginUser(@RequestBody UserRequestDTO usereRequest){
		return new ResponseEntity<UserResponseDTO>(userService.loginUser(usereRequest),HttpStatus.OK);
	}
	
	@PutMapping("resetPassword")
	public ResponseEntity<String> resetPassword(@RequestBody UserRequestDTO userRequest){
		return new ResponseEntity<String>(userService.resetPassword(userRequest),HttpStatus.OK);
	}
	
}
