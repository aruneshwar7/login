package com.auth.vibe.login.payload;

import com.auth.vibe.login.model.ProductModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductHtmlResponseDTO {
    private String content;
    Integer pageNumber;
    Integer pageSize;
    Integer totalPages;
    Long totalElements;
    Boolean lastPage;
}
