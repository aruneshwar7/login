package com.auth.vibe.login.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequestDTO {

    private Long id;
    @NotBlank(message = "product name is required")
    @Size(min = 3, max = 50, message = "product name must be between 3 and 50 characters")
    @Column(unique = true, nullable = false)
    private String name;
    @NotBlank(message = "imageUrl is required")
    @Size(min = 3, max = 254, message = "imageUrl must be between 3 and 254 characters")
    @Column( nullable = false)
    private String imageUrl;
    @Column(nullable = false)
    private Long categoryId;

    @NotNull(message = "quantity cannot be null")
    @Positive(message = "quantity must be greater than 0")
    private double basePrice;

    @NotNull(message = "quantity cannot be null")
    @Positive(message = "quantity must be greater than 0")
    private int quantity;


}
