package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Users;

public interface UserRepository extends CrudRepository<Users, Integer> {
    Optional<Users> findByStudentId(String studentId);
    
    @Query(value = "select a from Users a where studentId = ?1")
    Users findUser(String studentId);
}
