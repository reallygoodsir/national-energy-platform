package com.really.good.sir.energy.dto.response;

import java.util.List;

public class UserResponse {

    private Long id;
    private String email;
    private List<String> roles;

    public UserResponse() {
    }

    public UserResponse(
            Long id,
            String email,
            List<String> roles) {
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getRoles() {
        return roles;
    }
}