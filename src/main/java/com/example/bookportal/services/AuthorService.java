package com.example.bookportal.services;

import com.example.bookportal.exceptions.ResourceNotFoundException;
import com.example.bookportal.models.Author;
import com.example.bookportal.models.AuthorDTO;
import com.example.bookportal.models.Product;
import com.example.bookportal.models.ProductDTO;
import com.example.bookportal.repositories.AuthorRepository;
import com.example.bookportal.repositories.ProductRepository;
import com.example.bookportal.utils.AuthorDTOMapper;
import com.example.bookportal.utils.ProductDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AuthorDTOMapper authorDTOMapper;
    @Autowired
    private ProductDTOMapper productDTOMapper;

    public List<AuthorDTO> listAuthors() {
        return authorRepository.findAll().stream()
                .map(authorDTOMapper)
                .collect(Collectors.toList());
    }

    public Author saveAuthor(AuthorDTO authorDTO) {
        Author author = authorDTOMapper.toEntity(authorDTO);
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found with id " + id));
        authorRepository.delete(author);
    }

    public AuthorDTO getAuthorById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.map(authorDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id " + id));
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

    public List<ProductDTO> getProductsByAuthor(AuthorDTO authorDTO) {
        Author author = authorDTOMapper.toEntity(authorDTO);
        return productRepository.findByAuthor(author).stream()
                .map(productDTOMapper)
                .collect(Collectors.toList());
    }
}
