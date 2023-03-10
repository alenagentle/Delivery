package com.guavapay.delivery.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

    @NotBlank(message = "{mail.not-blank}")
    @Pattern(regexp = "^[a-zA-Z\\d_.+-]{2,}+@[a-zA-Z\\d-]{2,}\\.[a-zA-Z\\d-.]{2,}$", message = "{mail.format}")
    @Size(min = 8, max = 320, message = "{mail.size}")
    private String email;

    @NotBlank(message = "{password.not-blank}")
    @Pattern(regexp = "(?=.*\\d.*)(?=.*[A_Za-z].*)[A-Za-z\\d~`! @#$%^&*()_\\-+={\\[}\\]|:;\"'<,>.?/]+",
            message = "{password.format}")
    @Size(min = 6, max = 255, message = "{password.size}")
    private String password;
}
