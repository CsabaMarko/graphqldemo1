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
public class CustomerController {
    private final CustomerRepository customerRepository;

    @QueryMapping(name = "customers")
    public Page<Customer> list(@Argument int page, @Argument int size) {
        return customerRepository.findAll(PageRequest.of(page - 1, size));
    }

    @QueryMapping(name = "customerById")
    public Optional<Customer> findById(@Argument Long id) {
        return customerRepository.findById(id);
    }

}
