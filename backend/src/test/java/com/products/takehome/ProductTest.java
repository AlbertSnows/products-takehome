package com.products.takehome;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    void setNameTrimsLeadingAndTrailingWhitespace() {
        Product p = new Product();
        p.setName("  foo  ");
        assertThat(p.getName()).isEqualTo("foo");
    }
}
