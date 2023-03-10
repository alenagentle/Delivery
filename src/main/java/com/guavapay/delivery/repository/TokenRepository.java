package com.guavapay.delivery.repository;

import com.guavapay.delivery.entity.Token;
import com.guavapay.delivery.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    boolean existsTokensByUserData(UserData userData);

    Optional<Token> findTokenByUserData(UserData userData);

    boolean existsTokenByRefreshTokenAndRefreshTokenExpiresGreaterThanEqual(String refreshToken, Instant currentDate);

    boolean existsTokenByAccessTokenAndAccessTokenExpiresGreaterThanEqual(String accessToken, Instant currentDate);

    void deleteTokenByUserData(UserData userData);

}
