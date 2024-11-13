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
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
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

    public List<Product> findByAuthor(String authorName) {
        String[] authors = authorName.split(" ");
        Optional<Author> authorOptional = authorRepository.findByNameAndSurname(authors[0], authors[1]);
        if (authorOptional.isPresent()) {
            return productRepository.findByAuthor(authorOptional.get());
        }
        throw new ResourceNotFoundException("Author not found with name " + authorName);
    }
}
