package com.auth.vibe.login.security.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateOtpDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username must be alphanumeric")
    private String username;

    
    @NotBlank(message = "OTP is required")
    @Size(min = 4, max = 4, message = "OTP must be 4 characters")
    @Pattern(regexp = "^[0-9]*$", message = "OTP must be numeric")
    private String otp;
}
