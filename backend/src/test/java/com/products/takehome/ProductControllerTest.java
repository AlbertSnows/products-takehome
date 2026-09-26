package com.products.takehome;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.hasSize;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductRepository repository;

    @Test
    void listReturnsProductsFromRepository() throws Exception {
        Product p = new Product();
        p.setName("foo");
        when(repository.findAll()).thenReturn(List.of(p));
        
        mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].name").value("foo"));

    }

    @Test
    void listReturnsEmptyArrayWhenThereAreNoProducts() throws Exception {
        mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(content().json("[]"));
    }

    @Test
    void createReturns201WithTheSavedProduct() throws Exception {
        when(repository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));
        
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"foo\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("foo"));
    }

    @Test
    void createTrimsWhitespaceFromTheName() throws Exception {
        when(repository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));
        
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"     foo     \"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("foo"));
    }

    @Test
    void createWithBlankNameReturns400AndDoesNotSave() throws Exception {
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(content().json("[{\"field\":\"name\",\"message\":\"must not be blank\"}]"));
        verify(repository, never()).save(any());
    }

    @Test
    void createWithMissingNameReturns400AndDoesNotSave() throws Exception {
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());
        verify(repository, never()).save(any());
        }

    @Test
    void createWithNameOver255CharactersReturns400() throws Exception {
        String longName = "x".repeat(256);
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\""+longName+"\"}"))
            .andExpect(status().isBadRequest());
        verify(repository, never()).save(any());        
    }

    @Test
    void createIgnoresAClientSuppliedId() throws Exception {
        when(repository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));
        
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"foo\", \"id\":\"123e4567-e89b-12d3-a456-426614174000\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("foo"));
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getId()).isNull();



    }
}
