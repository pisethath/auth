package com.example.demo.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
	private String token;
    private long expiresIn;

    public String getToken() {
        return token;
    }
}
