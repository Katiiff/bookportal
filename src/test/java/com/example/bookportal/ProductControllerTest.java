package com.example.bookportal;

import com.example.bookportal.controllers.ProductController;
import com.example.bookportal.models.Author;
import com.example.bookportal.models.AuthorDTO;
import com.example.bookportal.models.Product;
import com.example.bookportal.models.ProductDTO;
import com.example.bookportal.services.AuthorService;
import com.example.bookportal.services.ProductService;
import com.example.bookportal.utils.AuthorDTOMapper;
import com.example.bookportal.utils.ProductDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private AuthorService authorService;

    @Mock
    private ProductDTOMapper productDTOMapper;

    @Mock
    private AuthorDTOMapper authorDTOMapper;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testMainPage() throws Exception {

        Product product1 = new Product(1L, "Book1", "Fiction", "Description1", new Author());
        Product product2 = new Product(2L, "Book2", "Non-Fiction", "Description2", new Author());
        List<Product> products = Arrays.asList(product1, product2);

        ProductDTO productDTO1 = new ProductDTO(1L, "Book1", "Fiction", "Description1", null);
        ProductDTO productDTO2 = new ProductDTO(2L, "Book2", "Non-Fiction", "Description2", null);
        List<ProductDTO> productDTOs = Arrays.asList(productDTO1, productDTO2);

        Author author1 = new Author(1L, "John", "Doe", Arrays.asList(product1));
        Author author2 = new Author(2L, "Jane", "Smith", Arrays.asList(product2));
        List<Author> authors = Arrays.asList(author1, author2);

        AuthorDTO authorDTO1 = new AuthorDTO(1L, "John", "Doe", Arrays.asList(1L));
        AuthorDTO authorDTO2 = new AuthorDTO(2L, "Jane", "Smith", Arrays.asList(2L));
        List<AuthorDTO> authorDTOs = Arrays.asList(authorDTO1, authorDTO2);

        when(productService.getAllProducts()).thenReturn(products);
        when(productDTOMapper.toDTOs(products)).thenReturn(productDTOs);
        when(authorService.listAuthors()).thenReturn(authors);
        when(authorDTOMapper.toDTOs(authors)).thenReturn(authorDTOs);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attribute("products", productDTOs))
                .andExpect(model().attribute("authors", authorDTOs));
    }

    @Test
    void testProductInfo() throws Exception {

        Long productId = 1L;
        Product product = new Product(productId, "Book1", "Fiction", "Description1", new Author());
        ProductDTO productDTO = new ProductDTO(productId, "Book1", "Fiction", "Description1", null);

        when(productService.getProductById(productId)).thenReturn(product);
        when(productDTOMapper.apply(product)).thenReturn(productDTO);

        mockMvc.perform(get("/product/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(view().name("product-info"))
                .andExpect(model().attribute("product", productDTO));
    }

    @Test
    void testCreateProduct() throws Exception {

        ProductDTO productDTO = new ProductDTO(null, "New Book", "Fiction", "New Description", null);
        Product product = new Product(1L, "New Book", "Fiction", "New Description", new Author());

        when(productDTOMapper.toEntity(productDTO)).thenReturn(product);

        mockMvc.perform(post("/product/create")
                        .flashAttr("productDTO", productDTO))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));

        verify(productService, times(1)).saveProduct(product);
    }

    @Test
    void testDeleteProduct() throws Exception {

        Long productId = 1L;

        mockMvc.perform(post("/product/delete/{id}", productId))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));

        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    public void testSearchByAuthor() {
        AuthorDTO authorDTO = new AuthorDTO(1L, "Name", "Surname", null);
        Author author = new Author();
        author.setName("Name");
        author.setSurname("Surname");
        List<Product> products = Arrays.asList(new Product(), new Product());
        List<ProductDTO> productDTOs = Arrays.asList(new ProductDTO(1L, "Title1", "Genre1", "Description1", null),
                new ProductDTO(2L, "Title2", "Genre2", "Description2", null));

        when(authorDTOMapper.toEntity(authorDTO)).thenReturn(author);
        when(productService.findByAuthor(author.getFullName())).thenReturn(products);
        when(productDTOMapper.toDTOs(products)).thenReturn(productDTOs);

        String viewName = productController.searchByAuthor(authorDTO, model);

        verify(model).addAttribute("products", productDTOs);
        assertEquals("index", viewName);
    }

    @Test
    public void testAuthors() {
        List<Author> authors = Arrays.asList(new Author(), new Author());
        List<AuthorDTO> authorDTOs = Arrays.asList(new AuthorDTO(1L, "Name1", "Surname1", null),
                new AuthorDTO(2L, "Name2", "Surname2", null));

        when(authorService.listAuthors()).thenReturn(authors);
        when(authorDTOMapper.toDTOs(authors)).thenReturn(authorDTOs);

        String viewName = productController.authors(model);

        verify(model).addAttribute("authors", authorDTOs);
        assertEquals("authors", viewName);
    }

    @Test
    void testAuthorInfo() throws Exception {

        Long authorId = 1L;
        Author author = new Author(authorId, "John", "Doe", null);
        Product product1 = new Product(1L, "Book1", "Fiction", "Description1", author);
        Product product2 = new Product(2L, "Book2", "Non-Fiction", "Description2", author);
        List<Product> products = Arrays.asList(product1, product2);

        ProductDTO productDTO1 = new ProductDTO(1L, "Book1", "Fiction", "Description1", null);
        ProductDTO productDTO2 = new ProductDTO(2L, "Book2", "Non-Fiction", "Description2", null);
        List<ProductDTO> productDTOs = Arrays.asList(productDTO1, productDTO2);

        AuthorDTO authorDTO = new AuthorDTO(authorId, "John", "Doe", null);

        when(authorService.getAuthorById(authorId)).thenReturn(author);
        when(productService.findByAuthor(author.getFullName())).thenReturn(products);
        when(authorDTOMapper.apply(author)).thenReturn(authorDTO);
        when(productDTOMapper.toDTOs(products)).thenReturn(productDTOs);

        mockMvc.perform(get("/author/{id}", authorId))
                .andExpect(status().isOk())
                .andExpect(view().name("author-info"))
                .andExpect(model().attribute("author", authorDTO))
                .andExpect(model().attribute("products", productDTOs));
    }

    @Test
    void testDeleteAuthor() throws Exception {

        Long authorId = 1L;

        mockMvc.perform(post("/author/delete/{id}", authorId))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));

        verify(authorService, times(1)).deleteAuthor(authorId);
    }

    @Test
    void testCreateAuthor() throws Exception {

        AuthorDTO authorDTO = new AuthorDTO(null, "New", "Author", null);
        Author author = new Author(1L, "New", "Author", null);

        when(authorDTOMapper.toEntity(authorDTO)).thenReturn(author);

        mockMvc.perform(post("/author/create")
                        .flashAttr("authorDTO", authorDTO))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));

        verify(authorService, times(1)).saveAuthor(author);
    }
}
