package com.products.takehome;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
// Why Cross Origin? 
// An origin is a schem, host, and port together. Our page is
// set up at http:localhost:5173, our api is set up at 
// http:localhost:8080. Because the ports differ, the sources
// our systems exist on different origins. Browsers typically
// restrict cross-origin requests that scripts start. CORS
// is a header mechanism that lets a server communicate which
// other orgins may read its responses. In other words, this
// annotation gives the FE "permission" to read our response.
@CrossOrigin(origins={"http://localhost:5173", "http://localhost:3000"})
public class ProductController {
    private final ProductRepository repository;
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);


    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping 
    public List<Product> list() {
        return repository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping 
    public Product create(@Valid @RequestBody CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.name());
        log.info("creating name: {}", product.getName());
        return repository.save(product);
    }
}