package com.csabamarko.springboot.jpa.graphqldemo1.snow;

import com.csabamarko.springboot.jpa.graphqldemo1.ReadOnlyPageableRepository;

public interface UserRepository extends ReadOnlyPageableRepository<User, String> {
}
