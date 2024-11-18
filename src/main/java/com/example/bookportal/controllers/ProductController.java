package com.example.bookportal.controllers;

import com.example.bookportal.models.Author;
import com.example.bookportal.models.Product;
import com.example.bookportal.models.ProductDTO;
import com.example.bookportal.services.AuthorService;
import com.example.bookportal.services.ProductService;
import com.example.bookportal.utils.AuthorDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final AuthorService authorService;
    private final AuthorDTOMapper authorDTOMapper;

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("authors", authorService.listAuthors());
        return "products";
    }

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "product-info";
    }

    @PostMapping("/product/create")
    public String createProduct(Product product) {
        productService.saveProduct(product);
        return "redirect:/";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }

    @GetMapping("/search/author")
    public String searchByAuthor(@RequestParam Author author, Model model) {
        List<ProductDTO> products = productService.findByAuthor(String.valueOf(author));
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/authors")
    public String authors(Model model) {
        model.addAttribute("authors", authorService.listAuthors());
        return "authors";
    }

    @GetMapping("/author/{id}")
    public String authorInfo(@PathVariable Long id, Model model) {
        Author author = authorDTOMapper.toEntity(authorService.getAuthorById(id));
        List<ProductDTO> products = productService.findByAuthor(author.getFullName());
        model.addAttribute("author", author);
        model.addAttribute("products", products);
        return "author-info";
    }



}
