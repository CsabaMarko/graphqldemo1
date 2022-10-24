package com.csabamarko.springboot.jpa.graphqldemo1.snow;

import com.csabamarko.springboot.jpa.graphqldemo1.ReadOnlyPageableRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends ReadOnlyPageableRepository<User, String>, JpaSpecificationExecutor<User> {
}
