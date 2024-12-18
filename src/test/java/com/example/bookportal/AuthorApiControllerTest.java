package com.example.bookportal;

import com.example.bookportal.controllers.AuthorApiController;
import com.example.bookportal.models.Author;
import com.example.bookportal.models.AuthorDTO;
import com.example.bookportal.models.Product;
import com.example.bookportal.models.ProductDTO;
import com.example.bookportal.services.AuthorService;
import com.example.bookportal.services.ProductService;
import com.example.bookportal.utils.AuthorDTOMapper;
import com.example.bookportal.utils.ProductDTOMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorApiController.class)
class AuthorApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private ProductService productService;

    @MockBean
    private AuthorDTOMapper authorDTOMapper;

    @MockBean
    private ProductDTOMapper productDTOMapper;

    @Test
    void getAllAuthors_shouldReturnAuthorList() throws Exception {
        AuthorDTO authorDTO = new AuthorDTO(1L, "John", "Doe", Collections.emptyList());
        Mockito.when(authorService.listAuthors()).thenReturn(Collections.singletonList(new Author()));
        Mockito.when(authorDTOMapper.toDTOs(any())).thenReturn(List.of(authorDTO));

        mockMvc.perform(get("/api/authors/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].surname").value("Doe"));
    }

    @Test
    void getBooksByAuthor_shouldReturnBookList() throws Exception {
        ProductDTO productDTO = new ProductDTO(1L, "Book Title", "Genre", "Description", null);
        Mockito.when(authorService.getAuthorById(1L)).thenReturn(new Author());
        Mockito.when(productService.getProductsByAuthor(any())).thenReturn(List.of(new Product()));
        Mockito.when(productDTOMapper.toDTOs(any())).thenReturn(List.of(productDTO));

        mockMvc.perform(get("/api/authors/1/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Book Title"));
    }

    @Test
    void createAuthor_shouldReturnCreatedAuthor() throws Exception {
        AuthorDTO inputDTO = new AuthorDTO(null, "Jane", "Smith", Collections.emptyList());
        AuthorDTO outputDTO = new AuthorDTO(1L, "Jane", "Smith", Collections.emptyList());
        Author author = new Author();

        Mockito.when(authorDTOMapper.toEntity(inputDTO)).thenReturn(author);
        Mockito.when(authorService.saveAuthor(author)).thenReturn(author);
        Mockito.when(authorDTOMapper.apply(author)).thenReturn(outputDTO);

        mockMvc.perform(post("/api/authors/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Jane"))
                .andExpect(jsonPath("$.surname").value("Smith"));
    }

    @Test
    void getAuthorById_shouldReturnAuthor() throws Exception {
        AuthorDTO authorDTO = new AuthorDTO(1L, "John", "Doe", Collections.emptyList());

        Mockito.when(authorService.getAuthorById(1L)).thenReturn(new Author());
        Mockito.when(authorDTOMapper.apply(any())).thenReturn(authorDTO);

        mockMvc.perform(get("/api/authors/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void updateAuthor_shouldReturnUpdatedAuthor() throws Exception {
        AuthorDTO inputDTO = new AuthorDTO(null, "Updated", "Author", Collections.emptyList());
        AuthorDTO outputDTO = new AuthorDTO(1L, "Updated", "Author", Collections.emptyList());
        Author author = new Author();

        Mockito.when(authorDTOMapper.toEntity(inputDTO)).thenReturn(author);
        Mockito.when(authorService.updateAuthor(eq(1L), any())).thenReturn(author);
        Mockito.when(authorDTOMapper.apply(author)).thenReturn(outputDTO);

        mockMvc.perform(put("/api/authors/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    public void testDeleteAuthor() throws Exception {
        Long authorId = 1L;

        mockMvc.perform(delete("/api/authors/delete/{id}", authorId))
                .andExpect(status().isOk());

        verify(authorService).deleteAuthor(authorId);
    }
}
