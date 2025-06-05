package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dtos.LoginUserDto;
import com.example.demo.dtos.RegisterUserDto;
import com.example.demo.model.Student;
import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
public class AuthenticationService {
	private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;
    
    @Autowired
    private RestTemplate restTemplate;

    public AuthenticationService(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users signup(RegisterUserDto input) {
        Users user = new Users();
        user.setStudentId(input.getStudentId());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    public Users authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getStudentId(),
                        input.getPassword()
                )
        );

        return userRepository.findByStudentId(input.getStudentId())
                .orElseThrow();
    }
    
    public Users findUser(String studentId){
        	return userRepository.findUser(studentId);
    }
    
    public Student findStudent(String studentId){
        if(studentId.length() > 1){
        	Student student = restTemplate.getForObject("http://STUDENT-SERVICE/student/id?studentid="+studentId, Student.class);
        	return student;
        }else{
            return new Student();
        }
    }
}
