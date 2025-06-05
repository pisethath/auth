package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.LoginResponse;
import com.example.demo.dtos.LoginUserDto;
import com.example.demo.dtos.RegisterUserDto;
import com.example.demo.model.ErrorResponse;
import com.example.demo.model.Student;
import com.example.demo.model.Users;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.JwtService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin("*")
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
private final JwtService jwtService;
    
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
    	try {
    		Student student = authenticationService.findStudent(registerUserDto.getStudentId());
    		Users user = authenticationService.findUser(registerUserDto.getStudentId());
        	if(student != null) {
        		if(user == null) {
        			Users registeredUser = authenticationService.signup(registerUserDto);
            		return new ResponseEntity<>(registeredUser, HttpStatus.OK);
        		}else {
        			ErrorResponse error = errorOnExist();
        			return new ResponseEntity<>(error, HttpStatus.OK);
        		}
        	}else {
        		ErrorResponse error = errorNotFound();
    			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        	}
    	}catch(Exception e) {
    		ErrorResponse error = errorBadRequest();
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    	}
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto) {
    	try {
	        Users authenticatedUser = authenticationService.authenticate(loginUserDto);
	
	        String jwtToken = jwtService.generateToken(authenticatedUser);
	
	        LoginResponse loginResponse = new LoginResponse();
	        loginResponse.setToken(jwtToken);
	        loginResponse.setExpiresIn(jwtService.getExpirationTime());
	
	        return ResponseEntity.ok(loginResponse);
    	}catch(Exception e) {
    		ErrorResponse error = errorBadRequest();
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    	}
    }
    
    private ErrorResponse errorOnExist() {
		ErrorResponse error = new ErrorResponse();
		error.setCode("400");
		error.setMessage("User is already existed.");
		
		return error;
	}
    
    private ErrorResponse errorNotFound() {
		ErrorResponse error = new ErrorResponse();
		error.setCode("404");
		error.setMessage("Student ID is not found.");
		
		return error;
	}
	
	private ErrorResponse errorBadRequest() {
		ErrorResponse error = new ErrorResponse();
		error.setCode("400");
		error.setMessage("Your request is not acceptable.");
		
		return error;
	}
}
