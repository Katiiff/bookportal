package com.example.bookportal;

import com.example.bookportal.models.Author;
import com.example.bookportal.models.AuthorDTO;
import com.example.bookportal.models.Product;
import com.example.bookportal.services.ProductService;
import com.example.bookportal.utils.AuthorDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AuthorDTOMapperTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private AuthorDTOMapper authorDTOMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testApply() {
        Author author = new Author();
        author.setId(1L);
        author.setName("John");
        author.setSurname("Doe");
        author.setProducts(Arrays.asList(new Product(1L, "Title1", "Genre1", "Description1", author),
                new Product(2L, "Title2", "Genre2", "Description2", author)));

        List<Product> products = Arrays.asList(new Product(1L, "Title1", "Genre1", "Description1", author),
                new Product(2L, "Title2", "Genre2", "Description2", author));

        when(productService.getProductsByIds(Arrays.asList(1L, 2L))).thenReturn(products);

        AuthorDTO authorDTO = authorDTOMapper.apply(author);

        assertEquals(1L, authorDTO.id());
        assertEquals("John", authorDTO.name());
        assertEquals("Doe", authorDTO.surname());
        assertEquals(Arrays.asList(1L, 2L), authorDTO.productsIds());
    }

    @Test
    public void testToEntity() {
        AuthorDTO authorDTO = new AuthorDTO(1L, "John", "Doe", Arrays.asList(1L, 2L));

        Author author = authorDTOMapper.toEntity(authorDTO);

        assertEquals(1L, author.getId());
        assertEquals("John", author.getName());
        assertEquals("Doe", author.getSurname());
        assertEquals(2, author.getProducts().size());
        assertEquals(1L, author.getProducts().get(0).getId());
        assertEquals(2L, author.getProducts().get(1).getId());
    }

    @Test
    public void testToDTOs() {
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("John");
        author1.setSurname("Doe");
        author1.setProducts(Arrays.asList(new Product(1L, "Title1", "Genre1", "Description1", author1)));

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Jane");
        author2.setSurname("Smith");
        author2.setProducts(Arrays.asList(new Product(2L, "Title2", "Genre2", "Description2", author2)));

        List<Author> authors = Arrays.asList(author1, author2);

        when(productService.getProductsByIds(Arrays.asList(1L))).thenReturn(Arrays.asList(new Product(1L, "Title1", "Genre1", "Description1", author1)));
        when(productService.getProductsByIds(Arrays.asList(2L))).thenReturn(Arrays.asList(new Product(2L, "Title2", "Genre2", "Description2", author2)));

        List<AuthorDTO> authorDTOs = authorDTOMapper.toDTOs(authors);

        assertEquals(2, authorDTOs.size());
        assertEquals(1L, authorDTOs.get(0).id());
        assertEquals("John", authorDTOs.get(0).name());
        assertEquals("Doe", authorDTOs.get(0).surname());
        assertEquals(Arrays.asList(1L), authorDTOs.get(0).productsIds());
        assertEquals(2L, authorDTOs.get(1).id());
        assertEquals("Jane", authorDTOs.get(1).name());
        assertEquals("Smith", authorDTOs.get(1).surname());
        assertEquals(Arrays.asList(2L), authorDTOs.get(1).productsIds());
    }

    @Test
    public void testToEntities() {
        AuthorDTO authorDTO1 = new AuthorDTO(1L, "John", "Doe", Arrays.asList(1L));
        AuthorDTO authorDTO2 = new AuthorDTO(2L, "Jane", "Smith", Arrays.asList(2L));

        List<AuthorDTO> authorDTOs = Arrays.asList(authorDTO1, authorDTO2);

        List<Author> authors = authorDTOMapper.toEntities(authorDTOs);

        assertEquals(2, authors.size());
        assertEquals(1L, authors.get(0).getId());
        assertEquals("John", authors.get(0).getName());
        assertEquals("Doe", authors.get(0).getSurname());
        assertEquals(1, authors.get(0).getProducts().size());
        assertEquals(1L, authors.get(0).getProducts().get(0).getId());
        assertEquals(2L, authors.get(1).getId());
        assertEquals("Jane", authors.get(1).getName());
        assertEquals("Smith", authors.get(1).getSurname());
        assertEquals(1, authors.get(1).getProducts().size());
        assertEquals(2L, authors.get(1).getProducts().get(0).getId());
    }
}
