package com.csabamarko.springboot.jpa.graphqldemo1.snow;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;

    @QueryMapping(name = "products")
    public Page<Product> list(@Argument int page, @Argument int size) {
        return productRepository.findAll(PageRequest.of(page - 1, size));
    }

    @QueryMapping(name = "productById")
    public Optional<Product> findById(@Argument Long id) {
        return productRepository.findById(id);
    }

}
