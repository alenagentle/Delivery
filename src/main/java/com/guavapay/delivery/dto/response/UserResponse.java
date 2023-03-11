package com.guavapay.delivery.dto.response;

import com.guavapay.delivery.entity.enums.RoleName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String email;
    private List<RoleName> roles;
}
