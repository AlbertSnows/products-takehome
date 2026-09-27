package com.products.takehome;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasItem;

// starts up our app similar to main(), including our server
@SpringBootTest
// wires up mockmvc, allows requests to be sent to server
@AutoConfigureMockMvc
// transactional ensures nothing is perisisted to the db
@Transactional
class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createdProductAppearsInTheList() throws Exception {
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"integration-marker\"}"))
            .andExpect(status().isCreated());

        mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[*].name", hasItem("integration-marker")));

    }
}
