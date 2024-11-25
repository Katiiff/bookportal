package com.example.bookportal.services;

import com.example.bookportal.exceptions.ResourceNotFoundException;
import com.example.bookportal.models.Author;
import com.example.bookportal.models.Product;
import com.example.bookportal.repositories.AuthorRepository;
import com.example.bookportal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorService authorService;

    public List<Product> getProductsByIds(List<Long> ids) {
        return productRepository.findAllById(ids);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        Author author = authorService.findOrCreateAuthor(product.getAuthor().getName(), product.getAuthor().getSurname());
        product.setAuthor(author);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    Author author = authorService.findOrCreateAuthor(updatedProduct.getAuthor().getName(), updatedProduct.getAuthor().getSurname());
                    product.setTitle(updatedProduct.getTitle());
                    product.setAuthor(author);
                    product.setDescription(updatedProduct.getDescription());
                    product.setGenre(updatedProduct.getGenre());
                    return saveProduct(product);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    public List<Product> findByAuthor(String authorName) {
        String[] authors = authorName.split(" ");
        List<Author> authorsList = authorRepository.findByNameAndSurname(authors[0], authors[1]);
        if (authorsList.isEmpty()) {
            throw new ResourceNotFoundException("Author not found with name " + authorName);
        }
        return authorsList.stream()
                .flatMap(author -> productRepository.findByAuthor(author).stream())
                .toList();
    }

}