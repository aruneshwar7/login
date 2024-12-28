package com.auth.vibe.login.security.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetDto {

    private String username;
    private String email;
    private String phone;
}
