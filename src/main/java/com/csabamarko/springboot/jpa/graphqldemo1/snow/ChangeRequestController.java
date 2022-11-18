package com.csabamarko.springboot.jpa.graphqldemo1.snow;

import com.csabamarko.springboot.jpa.graphqldemo1.common.ControllerBase;
import com.csabamarko.springboot.jpa.graphqldemo1.common.filter.FilterConverterComponent;
import com.csabamarko.springboot.jpa.graphqldemo1.common.filter.JpaSpecificationCreatorComponent;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Optional;

@Controller
public class ChangeRequestController extends ControllerBase<ChangeRequest, String> {

    public ChangeRequestController(ChangeRequestRepository repository,
                                   FilterConverterComponent<ChangeRequest, String> filterConverter,
                                   JpaSpecificationCreatorComponent<ChangeRequest, String> jpaSpecificationCreator) {
        super(repository, filterConverter, jpaSpecificationCreator);
    }

    @QueryMapping(name = "changeRequests")
    public Page<ChangeRequest> list(@Argument Map<String, Object> args, @Argument int page, @Argument int size) {
        return super.simpleFilteredList(ChangeRequest.class, args, page, size);
    }

    @QueryMapping(name = "changeRequestById")
    public Optional<ChangeRequest> findById(@Argument String id) {
        return super.findById(id);
    }

}
