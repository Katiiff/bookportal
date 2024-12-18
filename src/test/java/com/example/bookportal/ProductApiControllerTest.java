package com.example.bookportal;

import com.example.bookportal.controllers.ProductApiController;
import com.example.bookportal.models.Author;
import com.example.bookportal.models.Product;
import com.example.bookportal.models.ProductDTO;
import com.example.bookportal.services.ProductService;
import com.example.bookportal.utils.ProductDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductApiControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ProductDTOMapper productDTOMapper;

    @InjectMocks
    private ProductApiController productApiController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productApiController).build();
    }

    @Test
    void testGetAllProducts() throws Exception {

        Product product1 = new Product(1L, "Book1", "Fiction", "Description1", new Author());
        Product product2 = new Product(2L, "Book2", "Non-Fiction", "Description2", new Author());
        List<Product> products = Arrays.asList(product1, product2);

        ProductDTO productDTO1 = new ProductDTO(1L, "Book1", "Fiction", "Description1", null);
        ProductDTO productDTO2 = new ProductDTO(2L, "Book2", "Non-Fiction", "Description2", null);
        List<ProductDTO> productDTOs = Arrays.asList(productDTO1, productDTO2);

        when(productService.getAllProducts()).thenReturn(products);
        when(productDTOMapper.toDTOs(products)).thenReturn(productDTOs);

        mockMvc.perform(get("/api/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":1,\"title\":\"Book1\",\"genre\":\"Fiction\",\"description\":\"Description1\",\"authorDTO\":null},{\"id\":2,\"title\":\"Book2\",\"genre\":\"Non-Fiction\",\"description\":\"Description2\",\"authorDTO\":null}]"));
    }

    @Test
    void testCreateProduct() throws Exception {

        ProductDTO productDTO = new ProductDTO(null, "New Book", "Fiction", "New Description", null);
        Product product = new Product(1L, "New Book", "Fiction", "New Description", new Author());
        ProductDTO createdProductDTO = new ProductDTO(1L, "New Book", "Fiction", "New Description", null);

        when(productDTOMapper.toEntity(productDTO)).thenReturn(product);
        when(productService.saveProduct(product)).thenReturn(product);
        when(productDTOMapper.apply(product)).thenReturn(createdProductDTO);

        mockMvc.perform(post("/api/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Book\",\"genre\":\"Fiction\",\"description\":\"New Description\",\"authorDTO\":null}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"title\":\"New Book\",\"genre\":\"Fiction\",\"description\":\"New Description\",\"authorDTO\":null}"));
    }

    @Test
    void testGetProductById() throws Exception {

        Long productId = 1L;
        Product product = new Product(productId, "Book1", "Fiction", "Description1", new Author());
        ProductDTO productDTO = new ProductDTO(productId, "Book1", "Fiction", "Description1", null);

        when(productService.getProductById(productId)).thenReturn(product);
        when(productDTOMapper.apply(product)).thenReturn(productDTO);

        mockMvc.perform(get("/api/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"title\":\"Book1\",\"genre\":\"Fiction\",\"description\":\"Description1\",\"authorDTO\":null}"));
    }

    @Test
    void testUpdateProduct() throws Exception {

        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO(productId, "Updated Book", "Fiction", "Updated Description", null);
        Product product = new Product(productId, "Updated Book", "Fiction", "Updated Description", new Author());
        ProductDTO updatedProductDTO = new ProductDTO(productId, "Updated Book", "Fiction", "Updated Description", null);

        when(productDTOMapper.toEntity(productDTO)).thenReturn(product);
        when(productService.updateProduct(productId, product)).thenReturn(product);
        when(productDTOMapper.apply(product)).thenReturn(updatedProductDTO);

        mockMvc.perform(put("/api/update/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"title\":\"Updated Book\",\"genre\":\"Fiction\",\"description\":\"Updated Description\",\"authorDTO\":null}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"title\":\"Updated Book\",\"genre\":\"Fiction\",\"description\":\"Updated Description\",\"authorDTO\":null}"));
    }

    @Test
    void testDeleteProduct() throws Exception {

        Long productId = 1L;

        mockMvc.perform(delete("/api/delete/{id}", productId))
                .andExpect(status().isOk());
    }

    @Test
    void testSearchByAuthor() throws Exception {

        String authorName = "John Doe";
        Product product1 = new Product(1L, "Book1", "Fiction", "Description1", new Author());
        Product product2 = new Product(2L, "Book2", "Non-Fiction", "Description2", new Author());
        List<Product> products = Arrays.asList(product1, product2);

        ProductDTO productDTO1 = new ProductDTO(1L, "Book1", "Fiction", "Description1", null);
        ProductDTO productDTO2 = new ProductDTO(2L, "Book2", "Non-Fiction", "Description2", null);
        List<ProductDTO> productDTOs = Arrays.asList(productDTO1, productDTO2);

        when(productService.findByAuthor(authorName)).thenReturn(products);
        when(productDTOMapper.toDTOs(products)).thenReturn(productDTOs);

        mockMvc.perform(get("/api/search/author")
                        .param("author", authorName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":1,\"title\":\"Book1\",\"genre\":\"Fiction\",\"description\":\"Description1\",\"authorDTO\":null},{\"id\":2,\"title\":\"Book2\",\"genre\":\"Non-Fiction\",\"description\":\"Description2\",\"authorDTO\":null}]"));
    }
}
