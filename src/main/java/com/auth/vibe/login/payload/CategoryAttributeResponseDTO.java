package com.auth.vibe.login.payload;
import com.auth.vibe.login.model.CategoryAttributeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoryAttributeResponseDTO {
    List<CategoryAttributeModel> content;
}
