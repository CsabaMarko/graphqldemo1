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
public class ChangeRequestController {
    private final ChangeRequestRepository changeRequestRepository;

    @QueryMapping(name = "changeRequests")
    public Page<ChangeRequest> list(@Argument int page, @Argument int size) {
        return changeRequestRepository.findAll(PageRequest.of(page - 1, size));
    }

    @QueryMapping(name = "changeRequestById")
    public Optional<ChangeRequest> findById(@Argument String id) {
        return changeRequestRepository.findById(id);
    }

}
