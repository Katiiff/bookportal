package com.example.bookportal.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private long id;
    private String title;
    private String author;
    private String genre;
    private String description;

}
