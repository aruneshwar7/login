package com.auth.vibe.login.payload;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRequestDTO {

    private Long id;

    @NotBlank(message = "product name is required")
    @Size(min = 3, max = 50, message = "product name must be between 3 and 50 characters")
    @Column(unique = true, nullable = false)
    private String name;

    @NotBlank(message = "product name is required")
    @Size(min = 3, max = 50, message = "product description must be between 3 and 50 characters")
    @Column( nullable = false)
    private String description;

    @NotBlank(message = "imageUrl is required")
    @Size(min = 3, max = 254, message = "imageUrl must be between 3 and 254 characters")
    @Column( nullable = false)
    private String imageUrl;
    @NotBlank(message = "secondaryImageUrl is required")
    @Size(min = 3, max = 254, message = "secondaryImageUrl must be between 3 and 254 characters")
    @Column(nullable = false)
    private String secondaryImageUrl;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "sellingPrice must be greater than 0")
    private double sellingPrice;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "offerPrice must be greater than 0")
    private double offerPrice;


    @Column(nullable = false)
    private Long categoryId;
    @Column(nullable = false)
    private Long inventoryId;
    @Column(nullable = false)
    private Long stickerId;
}
