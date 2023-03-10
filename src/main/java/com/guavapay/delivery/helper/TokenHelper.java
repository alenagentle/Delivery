package com.guavapay.delivery.helper;

import com.guavapay.delivery.dto.request.RefreshTokenRequest;
import com.guavapay.delivery.dto.response.JwtResponse;
import com.guavapay.delivery.entity.Token;
import com.guavapay.delivery.entity.UserData;
import com.guavapay.delivery.exception.NotFoundException;
import com.guavapay.delivery.exception.UnvalidatedJwtException;
import com.guavapay.delivery.mapper.TokenMapper;
import com.guavapay.delivery.repository.TokenRepository;
import com.guavapay.delivery.security.dto.TokenInfo;
import com.guavapay.delivery.security.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class TokenHelper {

    private final JwtUtils jwtUtils;
    private final TokenMapper tokenMapper;
    private final TokenRepository tokenRepository;

    public JwtResponse generateTokens(UserData userData) {
        Token token = getToken(userData);
        tokenRepository.save(token);
        return tokenMapper.mapToResponse(token);
    }

    public JwtResponse refreshTokens(UserData userData, RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        validateRefreshToken(refreshToken);
        return generateTokens(userData);
    }

    public void invalidateTokens(UserData userData) {
        userData.setToken(null);
        tokenRepository.deleteTokenByUserData(userData);
    }

    public boolean validateAccessToken(String accessToken) {
        return tokenRepository.existsTokenByAccessTokenAndAccessTokenExpiresGreaterThanEqual(accessToken,
                Instant.now());
    }

    private void validateRefreshToken(String refreshToken) {
        jwtUtils.validateJwt(refreshToken);
        boolean tokenExists = tokenRepository.existsTokenByRefreshTokenAndRefreshTokenExpiresGreaterThanEqual(
                refreshToken, Instant.now());
        if (!tokenExists) {
            throw new UnvalidatedJwtException("Invalid token");
        }
    }

    private Token getToken(UserData userData) {
        String email = userData.getEmail();
        TokenInfo accessTokenInfo = jwtUtils.generateAccessToken(email);
        TokenInfo refreshTokenInfo = jwtUtils.generateRefreshToken(email);
        boolean tokenExists = tokenRepository.existsTokensByUserData(userData);
        if (tokenExists) {
            return updateToken(userData, accessTokenInfo, refreshTokenInfo);
        }

        return tokenMapper.mapToEntity(userData, accessTokenInfo, refreshTokenInfo);
    }

    private Token updateToken(UserData userData, TokenInfo accessTokenInfo, TokenInfo refreshTokenInfo) {
        Token token = tokenRepository.findTokenByUserData(userData)
                .orElseThrow(() -> new NotFoundException("Token not found"));
        token.setAccessToken(accessTokenInfo.getToken());
        token.setRefreshToken(refreshTokenInfo.getToken());
        token.setAccessTokenExpires(accessTokenInfo.getExpiresAt());
        token.setRefreshTokenExpires(refreshTokenInfo.getExpiresAt());
        return token;
    }

}
