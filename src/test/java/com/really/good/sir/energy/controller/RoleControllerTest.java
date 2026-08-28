package com.really.good.sir.energy.controller;

import com.really.good.sir.energy.dto.response.RoleResponse;
import com.really.good.sir.energy.service.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

    @Mock private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @Test
    void getAllRoles_returnsListFromService() {

        List<RoleResponse> expected = List.of(new RoleResponse(1L, "ADMIN", null));
        when(roleService.getAllRoles()).thenReturn(expected);

        List<RoleResponse> response = roleController.getAllRoles();

        assertThat(response).isEqualTo(expected);
    }
}