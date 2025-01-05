package com.auth.vibe.login.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StickerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank(message = "model name is required")
    @Size(min = 3, max = 50, message = "model name must be between 3 and 50 characters")
    @Column(unique = true, nullable = false)
    private String name;

    @NotBlank(message = "imageUrl is required")
    @Size(min = 3, max = 100, message = "imageUrl must be between 3 and 100 characters")
    @Column(unique = true, nullable = false)
    private String imageUrl;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be greater than 0")
    private double price;

    @OneToMany(mappedBy = "sticker")
    private List<ProductModel> product;

}
