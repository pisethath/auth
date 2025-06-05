package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private Integer id;
	private String studentId;
    private byte[] photo;
	private String name;
	private String phoneNumber;
	private String email;
}
