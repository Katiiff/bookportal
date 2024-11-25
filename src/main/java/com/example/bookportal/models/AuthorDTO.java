package com.example.bookportal.models;

import java.util.List;

public record AuthorDTO (
        Long id,
        String name,
        String surname,
        List<Long> productsIds
) {
    public String getFullName() {
        return name + " " + surname;
    }
}
