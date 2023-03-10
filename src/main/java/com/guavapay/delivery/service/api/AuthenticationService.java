package com.guavapay.delivery.service.api;

import com.guavapay.delivery.dto.request.RefreshTokenRequest;
import com.guavapay.delivery.dto.request.SignUpRequest;
import com.guavapay.delivery.dto.response.JwtResponse;

public interface AuthenticationService {

    JwtResponse registerUser(SignUpRequest request);

    JwtResponse refreshTokens(RefreshTokenRequest request);

    boolean validateAccessToken(String accessToken);

    void invalidateTokens();

}
