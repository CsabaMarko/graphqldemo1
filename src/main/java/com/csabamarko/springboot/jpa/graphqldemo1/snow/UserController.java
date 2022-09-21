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
public class UserController {
    private final UserRepository userRepository;

    @QueryMapping(name = "users")
    public Page<User> list(@Argument int page, @Argument int size) {
        return userRepository.findAll(PageRequest.of(page - 1, size));
    }

    @QueryMapping(name = "userById")
    public Optional<User> findById(@Argument String id) {
        return userRepository.findById(id);
    }
}