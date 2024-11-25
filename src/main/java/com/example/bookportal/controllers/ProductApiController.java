package com.example.bookportal.controllers;

import com.example.bookportal.models.Product;
import com.example.bookportal.models.ProductDTO;
import com.example.bookportal.services.ProductService;
import com.example.bookportal.utils.ProductDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;
    private final ProductDTOMapper productDTOMapper;

    @GetMapping("/all")
    public List<ProductDTO> getAllProducts() {
        return productDTOMapper.toDTOs(productService.getAllProducts());
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        Product createdProduct = productService.saveProduct(productDTOMapper.toEntity(productDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTOMapper.apply(createdProduct));
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productDTOMapper.apply(productService.getProductById(id));
    }

    @PutMapping("/update/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productDTOMapper.apply(productService.updateProduct(id, productDTOMapper.toEntity(productDTO)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/author")
    public List<ProductDTO> searchByAuthor(@RequestParam String author) {
        return productDTOMapper.toDTOs(productService.findByAuthor(author));
    }
}
