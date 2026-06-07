package com.really.good.sir.energy.dto.response;

import java.util.List;

public class SearchUserResponse {

    private Long userId;
    private String fullName;
    private List<RoleResponse> assignedRoles;
    private List<RoleResponse> availableRoles;

    public SearchUserResponse() {
    }

    public SearchUserResponse(
            Long userId,
            String fullName,
            List<RoleResponse> assignedRoles,
            List<RoleResponse> availableRoles) {

        this.userId = userId;
        this.fullName = fullName;
        this.assignedRoles = assignedRoles;
        this.availableRoles = availableRoles;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public List<RoleResponse> getAssignedRoles() {
        return assignedRoles;
    }

    public List<RoleResponse> getAvailableRoles() {
        return availableRoles;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAssignedRoles(List<RoleResponse> assignedRoles) {
        this.assignedRoles = assignedRoles;
    }

    public void setAvailableRoles(List<RoleResponse> availableRoles) {
        this.availableRoles = availableRoles;
    }
}