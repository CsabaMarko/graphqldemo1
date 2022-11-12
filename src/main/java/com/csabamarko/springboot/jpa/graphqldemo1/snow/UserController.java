package com.csabamarko.springboot.jpa.graphqldemo1.snow;

import com.csabamarko.springboot.jpa.graphqldemo1.common.ControllerBase;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Optional;

@Controller
public class UserController extends ControllerBase<User, String> {

    public UserController(UserRepository repository) {
        super(repository);
    }

    @QueryMapping(name = "users")
    public Page<User> list(@Argument Map<String, Object> args, @Argument int page,
                                         @Argument int size) {
        return super.simpleFilteredList(User.class, args, page, size);
    }

    @QueryMapping(name = "userById")
    public Optional<User> findById(@Argument String id) {
        return super.findById(id);
    }
}
