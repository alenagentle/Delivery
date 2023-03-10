package com.guavapay.delivery.controller;

import com.guavapay.delivery.dto.request.RefreshTokenRequest;
import com.guavapay.delivery.dto.request.SignUpRequest;
import com.guavapay.delivery.dto.response.JwtResponse;
import com.guavapay.delivery.service.api.AuthenticationService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping("/refresh")
    public JwtResponse refreshTokens(@RequestBody @Valid RefreshTokenRequest request) {
        return authenticationService.refreshTokens(request);
    }

    @PostMapping("/invalidate")
    public void invalidateTokens() {
        authenticationService.invalidateTokens();
    }

}
