package com.example.homeserver.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginDTO {
    @NotBlank(message = "Username must not be blank")
    private String username;
    @NotBlank(message = "Password must not be blank")
    private String password;

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
