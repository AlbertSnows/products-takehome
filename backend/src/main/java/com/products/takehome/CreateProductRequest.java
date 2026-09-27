package com.products.takehome;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// We use this to prevent the client from setting an ID
// Id's are auto generated, so we can use this to force the data
// coming into the endpoint to max our expectations.
public record CreateProductRequest(
    @NotBlank @Size(max = 255) String name
) {}
