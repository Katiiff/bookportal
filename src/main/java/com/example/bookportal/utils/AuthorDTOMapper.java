package com.example.bookportal.utils;

import com.example.bookportal.models.Author;
import com.example.bookportal.models.AuthorDTO;
import com.example.bookportal.models.Product;
import com.example.bookportal.models.ProductDTO;
import com.example.bookportal.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AuthorDTOMapper implements Function<Author, AuthorDTO> {
    private final ProductService productService;

    public AuthorDTOMapper(ProductService productService, ProductDTOMapper productDTOMapper) {
        this.productService = productService;
    }

    @Override
    public AuthorDTO apply(Author author) {
        List<Long> productIds = author.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        List<Product> products = productService.getProductsByIds(productIds);

        return new AuthorDTO(
                author.getId(),
                author.getName(),
                author.getSurname(),
                products.stream()
                        .map(Product::getId)
                        .collect(Collectors.toList())
        );
    }

    public Author toEntity(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setId(authorDTO.id());
        author.setName(authorDTO.name());
        author.setSurname(authorDTO.surname());

        List<Product> products = new ArrayList<>();
        if (authorDTO.productsIds() != null) {
            products = authorDTO.productsIds().stream()
                    .map(productId -> {
                        Product product = new Product();
                        product.setId(productId);
                        return product;
                    })
                    .collect(Collectors.toList());
        }
        author.setProducts(products);
        return author;
    }

    public List<AuthorDTO> toDTOs(List<Author> authors) {
        return authors.stream()
                .map(this::apply)
                .collect(Collectors.toList());
    }

    public List<Author> toEntities(List<AuthorDTO> authorDTOs) {
        return authorDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
