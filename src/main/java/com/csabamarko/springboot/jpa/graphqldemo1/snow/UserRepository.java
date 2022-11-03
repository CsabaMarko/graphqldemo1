package com.csabamarko.springboot.jpa.graphqldemo1.snow;

import com.csabamarko.springboot.jpa.graphqldemo1.ReadOnlyPageableRepository;
import com.csabamarko.springboot.jpa.graphqldemo1.common.RepositoryBase;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends RepositoryBase<User, String>,
        ReadOnlyPageableRepository<User, String>,
        JpaSpecificationExecutor<User> {
}
