package com.auth.vibe.login.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenderRequestDTO {
    private Integer id;
    private String name;
}
