package com.auth.vibe.login.payload;

import com.auth.vibe.login.model.GenderModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenderResponseDTO {
    private List<GenderModel> content;
}
