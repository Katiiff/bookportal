package com.example.bookportal.utils;

import com.example.bookportal.models.Product;
import com.example.bookportal.models.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductDTOMapper implements Function<Product, ProductDTO> {
    @Override
    public ProductDTO apply(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getTitle(),
                product.getGenre(),
                product.getDescription(),
                product.getAuthor()
        );
    }

    public Product toEntity(ProductDTO productDTO) {
        Product product = new Product();

        product.setId(productDTO.id());
        product.setTitle(productDTO.title());
        product.setGenre(productDTO.genre());
        product.setDescription(productDTO.description());

        return product;
    }
}
