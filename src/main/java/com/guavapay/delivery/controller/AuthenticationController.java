package com.guavapay.delivery.controller;

import com.guavapay.delivery.dto.request.LoginRequest;
import com.guavapay.delivery.dto.request.RefreshTokenRequest;
import com.guavapay.delivery.dto.request.SignUpRequest;
import com.guavapay.delivery.dto.response.JwtResponse;
import com.guavapay.delivery.service.api.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public JwtResponse registerUser(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.registerUser(request);
    }

    @PostMapping("/sign-in")
    public JwtResponse authenticateUser(@RequestBody @Valid LoginRequest request) {
        return authenticationService.login(request);
    }

    @PostMapping("/refresh")
    public JwtResponse refreshTokens(@RequestBody @Valid RefreshTokenRequest request) {
        return authenticationService.refreshTokens(request);
    }

    @PostMapping("/invalidate")
    public void invalidateTokens() {
        authenticationService.invalidateTokens();
    }

}
