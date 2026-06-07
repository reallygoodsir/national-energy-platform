package com.really.good.sir.energy.dto.request;

import jakarta.validation.constraints.NotNull;

public class RoleRequest {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Role id is required")
    private Long roleId;

    public RoleRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}