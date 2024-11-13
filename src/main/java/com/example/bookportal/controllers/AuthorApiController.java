package com.example.bookportal.controllers;

import com.example.bookportal.models.Author;
import com.example.bookportal.models.Product;
import com.example.bookportal.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorApiController {

    private final AuthorService authorService;

    @GetMapping("/all")
    public List<Author> authors() {
        return authorService.listAuthors();
    }

    @GetMapping("/{id}/books")
    public List<Product> author(@PathVariable long id) {
        return authorService.getProductsByAuthor(authorService.getAuthorById(id));
    }

    @PostMapping("/create")
    public Author createAuthor(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PutMapping("/update/{id}")
    public Author updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        return authorService.updateAuthor(id, author);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}
