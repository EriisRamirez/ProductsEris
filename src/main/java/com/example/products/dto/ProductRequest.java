package com.example.products.dto;

public record ProductRequest(
    @NotBlank
    String name,
    Double price,
    int stock
) {
}