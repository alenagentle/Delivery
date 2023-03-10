package com.guavapay.delivery.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "token")
@Getter
@Setter
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_data_id", nullable = false, unique = true)
    private UserData userData;

    @Column(name = "access_token", nullable = false, unique = true, length = 512)
    private String accessToken;

    @Column(name = "refresh_token", nullable = false, unique = true, length = 512)
    private String refreshToken;

    @Column(name = "access_token_expires", nullable = false)
    private Instant accessTokenExpires;

    @Column(name = "refresh_token_expires", nullable = false)
    private Instant refreshTokenExpires;
}
