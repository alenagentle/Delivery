package com.guavapay.delivery.mapper;

import com.guavapay.delivery.dto.response.JwtResponse;
import com.guavapay.delivery.entity.Token;
import com.guavapay.delivery.entity.UserData;
import com.guavapay.delivery.security.dto.TokenInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class TokenMapper {

    public Token mapToEntity(UserData userData, TokenInfo accessToken, TokenInfo refreshToken) {
        Token token = new Token();
        token.setUserData(userData);
        token.setAccessToken(accessToken.getToken());
        token.setRefreshToken(refreshToken.getToken());
        token.setAccessTokenExpires(accessToken.getExpiresAt());
        token.setRefreshTokenExpires(refreshToken.getExpiresAt());
        return token;
    }

    public abstract JwtResponse mapToResponse(Token token);
}
