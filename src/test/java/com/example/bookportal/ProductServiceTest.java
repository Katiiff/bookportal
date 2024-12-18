package com.example.bookportal;

import com.example.bookportal.exceptions.ResourceNotFoundException;
import com.example.bookportal.models.Author;
import com.example.bookportal.models.Product;
import com.example.bookportal.repositories.AuthorRepository;
import com.example.bookportal.repositories.ProductRepository;
import com.example.bookportal.services.AuthorService;
import com.example.bookportal.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProductsByIds() {

        List<Long> ids = Arrays.asList(1L, 2L);
        Product product1 = new Product(1L, "Book1", "Fiction", "Description1", new Author());
        Product product2 = new Product(2L, "Book2", "Non-Fiction", "Description2", new Author());
        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAllById(ids)).thenReturn(products);

        List<Product> result = productService.getProductsByIds(ids);

        assertEquals(2, result.size());
        assertEquals(product1, result.get(0));
        assertEquals(product2, result.get(1));
    }

    @Test
    void testGetAllProducts() {

        Product product1 = new Product(1L, "Book1", "Fiction", "Description1", new Author());
        Product product2 = new Product(2L, "Book2", "Non-Fiction", "Description2", new Author());
        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        assertEquals(product1, result.get(0));
        assertEquals(product2, result.get(1));
    }

    @Test
    void testSaveProduct() {

        Product product = new Product(1L, "Book1", "Fiction", "Description1", new Author(1L, "John", "Doe", null));
        Author author = new Author(1L, "John", "Doe", null);

        when(authorService.findOrCreateAuthor(any(String.class), any(String.class))).thenReturn(author);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.saveProduct(product);

        assertEquals(product, result);
    }

    @Test
    void testDeleteProduct() {

        Long productId = 1L;
        Product product = new Product(productId, "Book1", "Fiction", "Description1", new Author());

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteProductNotFound() {

        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(productId));
    }

    @Test
    void testGetProductById() {

        Long productId = 1L;
        Product product = new Product(productId, "Book1", "Fiction", "Description1", new Author());

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(productId);

        assertEquals(product, result);
    }

    @Test
    void testGetProductByIdNotFound() {

        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(productId));
    }

    @Test
    void testUpdateProduct() {

        Long productId = 1L;
        Product existingProduct = new Product(productId, "Book1", "Fiction", "Description1", new Author());
        Product updatedProduct = new Product(productId, "Updated Book", "Fiction", "Updated Description", new Author(1L, "John", "Doe", null));
        Author author = new Author(1L, "John", "Doe", null);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(authorService.findOrCreateAuthor(any(String.class), any(String.class))).thenReturn(author);
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct(productId, updatedProduct);

        assertEquals(updatedProduct, result);
    }

    @Test
    void testUpdateProductNotFound() {

        Long productId = 1L;
        Product updatedProduct = new Product(productId, "Updated Book", "Fiction", "Updated Description", new Author());

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(productId, updatedProduct));
    }

    @Test
    void testFindByAuthor() {

        String authorName = "John Doe";
        Author author = new Author(1L, "John", "Doe", null);
        Product product1 = new Product(1L, "Book1", "Fiction", "Description1", author);
        Product product2 = new Product(2L, "Book2", "Non-Fiction", "Description2", author);
        List<Product> products = Arrays.asList(product1, product2);

        when(authorRepository.findByNameAndSurname("John", "Doe")).thenReturn(Arrays.asList(author));
        when(productRepository.findByAuthor(author)).thenReturn(products);

        List<Product> result = productService.findByAuthor(authorName);

        assertEquals(2, result.size());
        assertEquals(product1, result.get(0));
        assertEquals(product2, result.get(1));
    }

    @Test
    void testFindByAuthorNotFound() {

        String authorName = "John Doe";

        when(authorRepository.findByNameAndSurname("John", "Doe")).thenReturn(Arrays.asList());

        assertThrows(ResourceNotFoundException.class, () -> productService.findByAuthor(authorName));
    }

    @Test
    void testGetProductsByAuthor() {

        Author author = new Author(1L, "John", "Doe", null);
        Product product1 = new Product(1L, "Book1", "Fiction", "Description1", author);
        Product product2 = new Product(2L, "Book2", "Non-Fiction", "Description2", author);
        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findByAuthor(author)).thenReturn(products);

        List<Product> result = productService.getProductsByAuthor(author);

        assertEquals(2, result.size());
        assertEquals(product1, result.get(0));
        assertEquals(product2, result.get(1));
    }
}
