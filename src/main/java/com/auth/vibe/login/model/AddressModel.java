package com.auth.vibe.login.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "House number or flat number is required")
    @Size(max = 100, message = "House number must not exceed 100 characters")
    private String houseNumber;

    @NotBlank(message = "Street is required")
    @Size(max = 150, message = "Street must not exceed 150 characters")
    private String street;

    @NotBlank(message = "Locality or area is required")
    @Size(max = 150, message = "Locality must not exceed 150 characters")
    private String locality;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 100, message = "State must not exceed 100 characters")
    private String state;

    @NotBlank(message = "PIN code is required")
    @Pattern(
            regexp = "^[1-9][0-9]{5}$",
            message = "PIN code must be a valid 6-digit number"
    )
    private String pinCode;

    @Size(max = 150, message = "Landmark must not exceed 150 characters")
    private String landmark;

    @Size(max = 100, message = "District must not exceed 100 characters")
    private String district;
}
