package com.example.bookportal;

import com.example.bookportal.exceptions.ResourceNotFoundException;
import com.example.bookportal.models.Author;
import com.example.bookportal.repositories.AuthorRepository;
import com.example.bookportal.services.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class AuthorServiceTest {

    @InjectMocks
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listAuthors_shouldReturnAllAuthors() {
        Author author = new Author(1L, "John", "Doe", Collections.emptyList());
        Mockito.when(authorRepository.findAll()).thenReturn(List.of(author));

        List<Author> authors = authorService.listAuthors();

        assertNotNull(authors);
        assertEquals(1, authors.size());
        assertEquals("John", authors.get(0).getName());
    }

    @Test
    void saveAuthor_shouldReturnSavedAuthor() {
        Author author = new Author(null, "Jane", "Smith", Collections.emptyList());
        Author savedAuthor = new Author(1L, "Jane", "Smith", Collections.emptyList());

        Mockito.when(authorRepository.save(author)).thenReturn(savedAuthor);

        Author result = authorService.saveAuthor(author);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Jane", result.getName());
    }

    @Test
    void deleteAuthor_shouldCallRepositoryDelete() {
        Author author = new Author(1L, "John", "Doe", Collections.emptyList());
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        assertDoesNotThrow(() -> authorService.deleteAuthor(1L));

        Mockito.verify(authorRepository, Mockito.times(1)).delete(author);
    }

    @Test
    void getAuthorById_shouldReturnAuthor() {
        Author author = new Author(1L, "John", "Doe", Collections.emptyList());
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Author result = authorService.getAuthorById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getName());
    }

    @Test
    void getAuthorById_shouldThrowExceptionWhenNotFound() {
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> authorService.getAuthorById(1L));

        assertEquals("Author not found with id 1", exception.getMessage());
    }

    @Test
    void updateAuthor_shouldUpdateAndReturnAuthor() {
        Author existingAuthor = new Author(1L, "John", "Doe", Collections.emptyList());
        Author updatedAuthor = new Author(null, "Jane", "Smith", Collections.emptyList());
        Author savedAuthor = new Author(1L, "Jane", "Smith", Collections.emptyList());

        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(existingAuthor));
        Mockito.when(authorRepository.save(any())).thenReturn(savedAuthor);

        Author result = authorService.updateAuthor(1L, updatedAuthor);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Jane", result.getName());
        assertEquals("Smith", result.getSurname());
    }

    @Test
    void findOrCreateAuthor_shouldReturnExistingAuthor() {
        Author existingAuthor = new Author(1L, "John", "Doe", Collections.emptyList());

        Mockito.when(authorRepository.findByNameAndSurname("John", "Doe"))
                .thenReturn(List.of(existingAuthor));

        Author result = authorService.findOrCreateAuthor("John", "Doe");

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getName());
    }

    @Test
    void findOrCreateAuthor_shouldCreateNewAuthorIfNotFound() {
        Mockito.when(authorRepository.findByNameAndSurname("Jane", "Smith"))
                .thenReturn(Collections.emptyList());

        Author newAuthor = new Author(null, "Jane", "Smith", Collections.emptyList());
        Author savedAuthor = new Author(1L, "Jane", "Smith", Collections.emptyList());

        Mockito.when(authorRepository.save(any())).thenReturn(savedAuthor);

        Author result = authorService.findOrCreateAuthor("Jane", "Smith");

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Jane", result.getName());
        assertEquals("Smith", result.getSurname());
    }
}

