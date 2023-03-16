package com.guavapay.delivery.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest()
class LoginRequestTest {

    private Validator validator;
    private Set<ConstraintViolation<LoginRequest>> violations;

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void whenLessSizePassword_thenValidationFails() {
        var loginRequest = buildLoginRequestWithLessSizePassword();
        violations = validator.validate(loginRequest);
        assertEquals(1, violations.size());
        assertEquals("{password.size}", violations.iterator()
                .next()
                .getMessage());
    }

    @Test
    public void whenWrongPatternPassword_thenValidationFails() {
        var loginRequest = buildLoginRequestPatternPassword();
        violations = validator.validate(loginRequest);
        assertEquals(1, violations.size());
        assertEquals("{password.format}", violations.iterator()
                .next()
                .getMessage());
    }

    private LoginRequest buildLoginRequestWithLessSizePassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("email@mail.ru");
        loginRequest.setPassword("aA12%");
        return loginRequest;
    }

    private LoginRequest buildLoginRequestPatternPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("email@mail.ru");
        loginRequest.setPassword("123456");
        return loginRequest;
    }

}