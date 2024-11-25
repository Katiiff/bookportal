package com.example.bookportal.utils;

import com.example.bookportal.models.Author;
import com.example.bookportal.models.AuthorDTO;
import com.example.bookportal.models.Product;
import com.example.bookportal.models.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductDTOMapper implements Function<Product, ProductDTO> {
    @Override
    public ProductDTO apply(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getTitle(),
                product.getGenre(),
                product.getDescription(),
                new AuthorDTO(
                        product.getAuthor().getId(),
                        product.getAuthor().getName(),
                        product.getAuthor().getSurname(),
                        product.getAuthor().getProducts().stream().map(Product::getId).toList()
                )
        );
    }

    public Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.id());
        product.setTitle(productDTO.title());
        product.setGenre(productDTO.genre());
        product.setDescription(productDTO.description());

        Author author = new Author();
        author.setId(productDTO.authorDTO().id());
        author.setName(productDTO.authorDTO().name());
        author.setSurname(productDTO.authorDTO().surname());

        product.setAuthor(author);

        return product;
    }

    public List<Product> toEntities(List<ProductDTO> productDTOList) {
        return productDTOList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> toDTOs(List<Product> products) {
        return products.stream()
                .map(this::apply)
                .collect(Collectors.toList());
    }
}
