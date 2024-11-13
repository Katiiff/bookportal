package com.example.bookportal.services;

import com.example.bookportal.exceptions.ResourceNotFoundException;
import com.example.bookportal.models.Author;
import com.example.bookportal.models.Product;
import com.example.bookportal.repositories.AuthorRepository;
import com.example.bookportal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Author author = authorRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Author not found with id " + id));
        authorRepository.delete(author);
    }

    public Author getAuthorById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.orElseThrow(() -> new ResourceNotFoundException("Author not found with id " + id));
    }

    public Author updateAuthor(Long id, Author updatedAuthor) {
        return authorRepository.findById(id).
                map(author -> {
                    author.setName(updatedAuthor.getName());
                    author.setSurname(updatedAuthor.getSurname());
                    return authorRepository.save(author);
                }).
                orElseThrow(() -> new ResourceNotFoundException("Author not found with id " + id));
    }

    public List<Product> getProductsByAuthor(Author author) {
        return productRepository.findByAuthor(author);
    }
}
