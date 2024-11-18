package com.example.bookportal.services;

import com.example.bookportal.exceptions.ResourceNotFoundException;
import com.example.bookportal.models.Author;
import com.example.bookportal.models.Product;
import com.example.bookportal.models.ProductDTO;
import com.example.bookportal.repositories.AuthorRepository;
import com.example.bookportal.repositories.ProductRepository;
import com.example.bookportal.utils.ProductDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private final ProductDTOMapper productDTOMapper;

    public ProductService(ProductDTOMapper productDTOMapper) {
        this.productDTOMapper = productDTOMapper;
    }

    public List<ProductDTO> getProductsByIds(List<Long> ids) {
        return productRepository.findAllById(ids)
                .stream()
                .map(productDTOMapper).
                collect(Collectors.toList());
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productDTOMapper).
                collect(Collectors.toList());
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    public ProductDTO getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.
                map(productDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setTitle(updatedProduct.getTitle());
                    product.setAuthor(updatedProduct.getAuthor());
                    product.setDescription(updatedProduct.getDescription());
                    product.setGenre(updatedProduct.getGenre());
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    public List<ProductDTO> findByAuthor(String authorName) {
        String[] authors = authorName.split(" ");
        List<Author> authorsList = authorRepository.findByNameAndSurname(authors[0], authors[1]);
        if (authorsList.isEmpty()) {
            throw new ResourceNotFoundException("Author not found with name " + authorName);
        }
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Author author : authorsList) {
            productDTOs.addAll(productRepository.findByAuthor(author).stream()
                    .map(productDTOMapper)
                    .collect(Collectors.toList()));
        }
        return productDTOs;
        }

    }
