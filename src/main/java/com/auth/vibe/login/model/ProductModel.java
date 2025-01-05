package com.auth.vibe.login.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "product name is required")
    @Size(min = 3, max = 50, message = "product name must be between 3 and 50 characters")
    @Column(unique = true, nullable = false)
    private String name;

    @NotBlank(message = "product name is required")
    @Size(min = 3, max = 50, message = "product description must be between 3 and 50 characters")
    @Column(unique = true, nullable = false)
    private String description;

    @NotBlank(message = "imageUrl is required")
    @Size(min = 3, max = 254, message = "imageUrl must be between 3 and 254 characters")
    @Column( nullable = false)
    private String imageUrl;
    @NotBlank(message = "secondaryImageUrl is required")
    @Size(min = 3, max = 254, message = "secondaryImageUrl must be between 3 and 254 characters")
    @Column( nullable = false)
    private String secondaryImageUrl;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryModel category;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    @JsonIgnore
    private InventoryModel inventory;

    @ManyToOne
    @JoinColumn(name = "sticker_id")
    @JsonIgnore
    private StickerModel sticker;

    @Column(nullable = false)
    private double sellingPrice;

    @Column(nullable = false)
    private double offerPrice;



}
