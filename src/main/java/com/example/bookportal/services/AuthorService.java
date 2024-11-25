package com.example.bookportal.services;

import com.example.bookportal.exceptions.ResourceNotFoundException;
import com.example.bookportal.models.Author;
import com.example.bookportal.repositories.AuthorRepository;
import com.example.bookportal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Author> listAuthors() {
        return authorRepository.findAll();
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        Author author = getAuthorById(id);
        authorRepository.delete(author);
    }

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id " + id));
    }

    public Author updateAuthor(Long id, Author updatedAuthor) {
        Author author = getAuthorById(id);
        author.setName(updatedAuthor.getName());
        author.setSurname(updatedAuthor.getSurname());
        return saveAuthor(author);
    }

    public Author findOrCreateAuthor(String name, String surname) {
        List<Author> authors = authorRepository.findByNameAndSurname(name, surname);
        if (authors.isEmpty()) {
            Author newAuthor = new Author();
            newAuthor.setName(name);
            newAuthor.setSurname(surname);
            return saveAuthor(newAuthor);
        }
        return authors.get(0);
    }
}
