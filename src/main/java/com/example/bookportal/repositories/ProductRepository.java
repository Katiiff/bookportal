package com.example.bookportal.repositories;

import com.example.bookportal.models.Author;
import com.example.bookportal.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByAuthor(Author author);
}
