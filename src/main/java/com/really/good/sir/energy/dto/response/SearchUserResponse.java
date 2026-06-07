package com.really.good.sir.energy.dto.response;

import java.util.List;

public class SearchUserResponse {

    private Long userId;
    private String fullName;
    private List<RoleDto> assignedRoles;
    private List<RoleDto> availableRoles;

    public SearchUserResponse() {
    }

    public SearchUserResponse(
            Long userId,
            String fullName,
            List<RoleDto> assignedRoles,
            List<RoleDto> availableRoles) {

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

    public List<RoleDto> getAssignedRoles() {
        return assignedRoles;
    }

    public List<RoleDto> getAvailableRoles() {
        return availableRoles;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAssignedRoles(List<RoleDto> assignedRoles) {
        this.assignedRoles = assignedRoles;
    }

    public void setAvailableRoles(List<RoleDto> availableRoles) {
        this.availableRoles = availableRoles;
    }
}