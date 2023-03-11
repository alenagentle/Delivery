package com.guavapay.delivery.controller;

import com.guavapay.delivery.dto.response.UserResponse;
import com.guavapay.delivery.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<UserResponse> findAllUsers() {
        return userService.findAllUsers();
    }

    @PutMapping("/admin/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserResponse setCourierRole(@PathVariable String email) {
        return userService.setCourierRole(email);
    }

}
