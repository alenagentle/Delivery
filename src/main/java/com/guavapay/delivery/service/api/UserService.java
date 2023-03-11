package com.guavapay.delivery.service.api;

import com.guavapay.delivery.dto.request.ChangePasswordRequest;
import com.guavapay.delivery.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> findAllUsers();
    UserResponse setCourierRole(String email);
    void changePassword(ChangePasswordRequest request);
}