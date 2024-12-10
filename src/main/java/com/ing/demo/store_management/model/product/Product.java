package com.ing.demo.store_management.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    @NotEmpty(message = "Name can not be empty")
    private String name;

    @Column(nullable = false)
    @NotEmpty(message = "Description can not be empty")
    private String description;

    @Column(nullable = false)
    @Positive(message = "Price must be positive")
    private double price;

    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "Category can not be empty")
    private Category category;
}
