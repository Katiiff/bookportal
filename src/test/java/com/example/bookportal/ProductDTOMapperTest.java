package com.example.bookportal;

import com.example.bookportal.models.Author;
import com.example.bookportal.models.AuthorDTO;
import com.example.bookportal.models.Product;
import com.example.bookportal.models.ProductDTO;
import com.example.bookportal.utils.ProductDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class ProductDTOMapperTest {

    private ProductDTOMapper productDTOMapper;

    @BeforeEach
    public void setUp() {
        productDTOMapper = new ProductDTOMapper();
    }

    @Test
    public void testApply() {
        Author author = new Author();
        author.setId(1L);
        author.setName("John");
        author.setSurname("Doe");
        author.setProducts(Arrays.asList(new Product(1L, "Title1", "Genre1", "Description1", author),
                new Product(2L, "Title2", "Genre2", "Description2", author)));

        Product product = new Product();
        product.setId(1L);
        product.setTitle("Title");
        product.setGenre("Genre");
        product.setDescription("Description");
        product.setAuthor(author);

        ProductDTO productDTO = productDTOMapper.apply(product);

        assertEquals(1L, productDTO.id());
        assertEquals("Title", productDTO.title());
        assertEquals("Genre", productDTO.genre());
        assertEquals("Description", productDTO.description());
        assertEquals(1L, productDTO.authorDTO().id());
        assertEquals("John", productDTO.authorDTO().name());
        assertEquals("Doe", productDTO.authorDTO().surname());
        assertEquals(Arrays.asList(1L, 2L), productDTO.authorDTO().productsIds());
    }

    @Test
    public void testToEntity() {
        AuthorDTO authorDTO = new AuthorDTO(1L, "John", "Doe", Arrays.asList(1L, 2L));
        ProductDTO productDTO = new ProductDTO(1L, "Title", "Genre", "Description", authorDTO);

        Product product = productDTOMapper.toEntity(productDTO);

        assertEquals(1L, product.getId());
        assertEquals("Title", product.getTitle());
        assertEquals("Genre", product.getGenre());
        assertEquals("Description", product.getDescription());
        assertEquals(1L, product.getAuthor().getId());
        assertEquals("John", product.getAuthor().getName());
        assertEquals("Doe", product.getAuthor().getSurname());
    }

    @Test
    public void testToEntities() {
        AuthorDTO authorDTO = new AuthorDTO(1L, "John", "Doe", Arrays.asList(1L, 2L));
        ProductDTO productDTO1 = new ProductDTO(1L, "Title1", "Genre1", "Description1", authorDTO);
        ProductDTO productDTO2 = new ProductDTO(2L, "Title2", "Genre2", "Description2", authorDTO);

        List<ProductDTO> productDTOList = Arrays.asList(productDTO1, productDTO2);

        List<Product> products = productDTOMapper.toEntities(productDTOList);

        assertEquals(2, products.size());
        assertEquals(1L, products.get(0).getId());
        assertEquals("Title1", products.get(0).getTitle());
        assertEquals("Genre1", products.get(0).getGenre());
        assertEquals("Description1", products.get(0).getDescription());
        assertEquals(2L, products.get(1).getId());
        assertEquals("Title2", products.get(1).getTitle());
        assertEquals("Genre2", products.get(1).getGenre());
        assertEquals("Description2", products.get(1).getDescription());
    }

    @Test
    public void testToDTOs() {
        Author author = new Author();
        author.setId(1L);
        author.setName("John");
        author.setSurname("Doe");
        author.setProducts(Arrays.asList(new Product(1L, "Title1", "Genre1", "Description1", author),
                new Product(2L, "Title2", "Genre2", "Description2", author)));

        Product product1 = new Product();
        product1.setId(1L);
        product1.setTitle("Title1");
        product1.setGenre("Genre1");
        product1.setDescription("Description1");
        product1.setAuthor(author);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setTitle("Title2");
        product2.setGenre("Genre2");
        product2.setDescription("Description2");
        product2.setAuthor(author);

        List<Product> products = Arrays.asList(product1, product2);

        List<ProductDTO> productDTOs = productDTOMapper.toDTOs(products);

        assertEquals(2, productDTOs.size());
        assertEquals(1L, productDTOs.get(0).id());
        assertEquals("Title1", productDTOs.get(0).title());
        assertEquals("Genre1", productDTOs.get(0).genre());
        assertEquals("Description1", productDTOs.get(0).description());
        assertEquals(2L, productDTOs.get(1).id());
        assertEquals("Title2", productDTOs.get(1).title());
        assertEquals("Genre2", productDTOs.get(1).genre());
        assertEquals("Description2", productDTOs.get(1).description());
    }
}