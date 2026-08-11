package com.really.good.sir.energy.dto.response;

import java.util.Collections;
import java.util.List;

public class SearchUserResponse {

    private Long userId;
    private String fullName;
    private List<RoleResponse> assignedRoles;
    private List<RoleResponse> availableRoles;

    public SearchUserResponse() {
    }

    public SearchUserResponse(
            final Long userId,
            final String fullName,
            final List<RoleResponse> assignedRoles,
            final List<RoleResponse> availableRoles) {

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
        return assignedRoles == null
                ? List.of()
                : Collections.unmodifiableList(assignedRoles);
    }

    public List<RoleResponse> getAvailableRoles() {
        return availableRoles == null
                ? List.of()
                : Collections.unmodifiableList(availableRoles);
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public void setAssignedRoles(final List<RoleResponse> assignedRoles) {
        this.assignedRoles = assignedRoles;
    }

    public void setAvailableRoles(final List<RoleResponse> availableRoles) {
        this.availableRoles = availableRoles;
    }
}