package com.example.bookportal.controllers;

import com.example.bookportal.models.Author;
import com.example.bookportal.models.AuthorDTO;
import com.example.bookportal.models.ProductDTO;
import com.example.bookportal.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorApiController {

    private final AuthorService authorService;

    @GetMapping("/all")
    public List<AuthorDTO> authors() {
        return authorService.listAuthors();
    }

    @GetMapping("/{id}/books")
    public List<ProductDTO> author(@PathVariable long id) {
        return authorService.getProductsByAuthor(authorService.getAuthorById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Author> createAuthor(@ModelAttribute AuthorDTO authorDTO) {
        Author createdAuthor = authorService.saveAuthor(authorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }

    @GetMapping("/{id}")
    public AuthorDTO getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PutMapping("/update/{id}")
    public Author updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        return authorService.updateAuthor(id, author);
    }

    @PostMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "Successfully deleted";
    }
}
