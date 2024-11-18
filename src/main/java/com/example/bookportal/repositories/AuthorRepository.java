package com.example.bookportal.repositories;

import com.example.bookportal.models.Author;
import com.example.bookportal.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByNameAndSurname(String authorName, String authorSurname);
}
