package com.example.homeserver.DTO;

import lombok.Getter;

@Getter
public class ResLoginDTO {
    private String username;
    private String accessToken;

    public ResLoginDTO(String username, String accessToken) {
        this.username = username;
        this.accessToken = accessToken;
    }

}
