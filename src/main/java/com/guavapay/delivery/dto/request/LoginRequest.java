package com.guavapay.delivery.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "{mail.not-blank}")
    @Size(min = 4, max = 320, message = "{mail.size}")
    private String email;

    @NotBlank(message = "{password.not-blank}")
    @Pattern(regexp = "(?=.*\\d.*)(?=.*[A_Za-z].*)[A-Za-z\\d~`! @#$%^&*()_\\-+={\\[}\\]|:;\"'<,>.?/]+",
            message = "{password.format}")
    @Size(min = 6, max = 255, message = "{password.size}")
    private String password;
}
