package com.example.bookportal.controllers;

import com.example.bookportal.models.Author;
import com.example.bookportal.models.AuthorDTO;
import com.example.bookportal.models.Product;
import com.example.bookportal.models.ProductDTO;
import com.example.bookportal.services.AuthorService;
import com.example.bookportal.services.ProductService;
import com.example.bookportal.utils.AuthorDTOMapper;
import com.example.bookportal.utils.ProductDTOMapper;
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
    private final ProductDTOMapper productDTOMapper;
    private final AuthorDTOMapper authorDTOMapper;

    @GetMapping("/")
    public String mainPage(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", productDTOMapper.toDTOs(products));
        model.addAttribute("authors", authorDTOMapper.toDTOs(authorService.listAuthors()));
        return "products";
    }

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model) {
        model.addAttribute("product", productDTOMapper.apply(productService.getProductById(id)));
        return "product-info";
    }

    @PostMapping("/product/create")
    public String createProduct(ProductDTO productDTO) {
        productService.saveProduct(productDTOMapper.toEntity(productDTO));
        return "redirect:/";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }

    @GetMapping("/search/author")
    public String searchByAuthor(@RequestParam AuthorDTO authorDTO, Model model) {
        List<Product> products = productService.findByAuthor((authorDTOMapper.toEntity(authorDTO)).getFullName());
        model.addAttribute("products", productDTOMapper.toDTOs(products));
        return "index";
    }

    @GetMapping("/authors")
    public String authors(Model model) {
        model.addAttribute("authors", authorDTOMapper.toDTOs(authorService.listAuthors()));
        return "authors";
    }

    @GetMapping("/author/{id}")
    public String authorInfo(@PathVariable Long id, Model model) {
        Author author = authorService.getAuthorById(id);
        List<Product> products = productService.findByAuthor(author.getFullName());
        model.addAttribute("author", authorDTOMapper.apply(author));
        model.addAttribute("products", productDTOMapper.toDTOs(products));
        return "author-info";
    }

    @PostMapping("/author/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "redirect:/";
    }

    @PostMapping("/author/create")
    public String createAuthor(AuthorDTO authorDTO) {
        authorService.saveAuthor(authorDTOMapper.toEntity(authorDTO));
        return "redirect:/";
    }

}
