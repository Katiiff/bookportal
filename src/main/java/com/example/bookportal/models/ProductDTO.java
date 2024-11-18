package com.example.bookportal.models;

public record ProductDTO (
        Long id,
        String title,
        String genre,
        String description,
        Author authorDTO
) {

    }