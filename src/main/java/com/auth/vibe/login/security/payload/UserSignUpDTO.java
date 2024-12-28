package com.auth.vibe.login.security.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
}
