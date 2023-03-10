package com.guavapay.delivery.service.impl;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.guavapay.delivery.dto.request.RefreshTokenRequest;
import com.guavapay.delivery.dto.request.SignUpRequest;
import com.guavapay.delivery.dto.response.JwtResponse;
import com.guavapay.delivery.entity.UserData;
import com.guavapay.delivery.exception.FieldAlreadyTakenException;
import com.guavapay.delivery.exception.UnvalidatedJwtException;
import com.guavapay.delivery.helper.TokenHelper;
import com.guavapay.delivery.mapper.UserMapper;
import com.guavapay.delivery.security.util.JwtUtils;
import com.guavapay.delivery.service.api.AuthenticationService;
import com.guavapay.delivery.helper.UserHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserHelper userHelper;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenHelper tokenHelper;
    private final JwtUtils jwtUtils;

    @Override
    @Transactional
    public JwtResponse registerUser(SignUpRequest request) {
        validateUserByEmail(request);
        UserData user = userMapper.mapToUser(request);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        UserData userData = userHelper.saveUser(user);
        return tokenHelper.generateTokens(userData);
    }

    @Override
    @Transactional
    public JwtResponse refreshTokens(RefreshTokenRequest request) {
        try {
            String email = jwtUtils.getUsernameFromJwt(request.getRefreshToken());
            UserData userData = userHelper.findUserByEmail(email);
            return tokenHelper.refreshTokens(userData, request);
        } catch (JWTDecodeException ex) {
            throw new UnvalidatedJwtException("Invalid token");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateAccessToken(String accessToken) {
        return tokenHelper.validateAccessToken(accessToken);
    }

    @Override
    @Transactional
    public void invalidateTokens() {
        UserData currentUserData = userHelper.getCurrentUserData();
        tokenHelper.invalidateTokens(currentUserData);
    }

    private void validateUserByEmail(SignUpRequest request) {

        if (userHelper.existsUserByEmail(request.getEmail())) {
            throw new FieldAlreadyTakenException(request.getEmail(), "Email");
        }
    }

}
