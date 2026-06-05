package com.really.good.sir.energy.dto.response;

public class SignupResponse {

    private Long id;
    private String fullName;
    private String email;

    public SignupResponse() {}

    public SignupResponse(Long id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }
}