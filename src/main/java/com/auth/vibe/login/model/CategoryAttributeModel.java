package com.auth.vibe.login.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAttributeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @NotBlank(message = "category attribute is required")
    @Size(min = 3, max = 50, message = "category attribute must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "imageUrl is required")
    @Size(min = 3, max = 100, message = "imageUrl must be between 3 and 100 characters")
    @Column(unique = true, nullable = false)
    private String imageUrl;



    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private CategoryModel category;
    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
