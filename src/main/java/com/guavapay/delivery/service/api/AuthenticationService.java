package com.guavapay.delivery.service.api;

import com.guavapay.delivery.dto.request.LoginRequest;
import com.guavapay.delivery.dto.request.RefreshTokenRequest;
import com.guavapay.delivery.dto.request.SignUpRequest;
import com.guavapay.delivery.dto.response.JwtResponse;

public interface AuthenticationService {

    JwtResponse registerUser(SignUpRequest request);

    JwtResponse login(LoginRequest loginRequest);

    JwtResponse refreshTokens(RefreshTokenRequest request);

    boolean validateAccessToken(String accessToken);

    void invalidateTokens();

}
