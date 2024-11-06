package com.example.bookportal.services;

import com.example.bookportal.models.Product;
import com.example.bookportal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setTitle(updatedProduct.getTitle());
            product.setAuthor(updatedProduct.getAuthor());
            product.setDescription(updatedProduct.getDescription());
            product.setGenre(updatedProduct.getGenre());
            return productRepository.save(product);
        }
        return null;
    }

    public List<Product> findByAuthor(String author) {
        return productRepository.findByAuthor(author);
    }
}
