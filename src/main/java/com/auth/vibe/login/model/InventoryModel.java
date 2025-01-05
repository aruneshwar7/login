package com.auth.vibe.login.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
    private double basePrice;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryModel category;
    @OneToMany(mappedBy = "inventory")
    private List<ProductModel>  product;

}
