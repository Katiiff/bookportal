package com.example.bookportal.controllers;

import com.example.bookportal.models.Author;
import com.example.bookportal.models.AuthorDTO;
import com.example.bookportal.models.ProductDTO;
import com.example.bookportal.services.AuthorService;
import com.example.bookportal.services.ProductService;
import com.example.bookportal.utils.AuthorDTOMapper;
import com.example.bookportal.utils.ProductDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorApiController {

    private final AuthorService authorService;
    private final AuthorDTOMapper authorDTOMapper;
    private final ProductDTOMapper productDTOMapper;
    private final ProductService productService;

    @GetMapping("/all")
    public List<AuthorDTO> getAllAuthors() {
        return authorDTOMapper.toDTOs(authorService.listAuthors());
    }

    @GetMapping("/{id}/books")
    public List<ProductDTO> getBooksByAuthor(@PathVariable long id) {
        return productDTOMapper.toDTOs(productService.getProductsByAuthor(authorService.getAuthorById(id)));
    }

    @PostMapping("/create")
    public AuthorDTO createAuthor(@RequestBody AuthorDTO authorDTO) {
        Author createdAuthor = authorService.saveAuthor(authorDTOMapper.toEntity(authorDTO));
        return authorDTOMapper.apply(createdAuthor);
    }

    @GetMapping("/{id}")
    public AuthorDTO getAuthorById(@PathVariable Long id) {
        return authorDTOMapper.apply(authorService.getAuthorById(id));
    }

    @PutMapping("/update/{id}")
    public AuthorDTO updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        return authorDTOMapper.apply(authorService.updateAuthor(id, authorDTOMapper.toEntity(authorDTO)));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}
