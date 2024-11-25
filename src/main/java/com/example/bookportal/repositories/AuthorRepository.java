package com.example.bookportal.repositories;

import com.example.bookportal.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByNameAndSurname(String authorName, String authorSurname);
}
