package com.csabamarko.springboot.jpa.graphqldemo1.snow;

import com.csabamarko.springboot.jpa.graphqldemo1.ReadOnlyPageableRepository;
import com.csabamarko.springboot.jpa.graphqldemo1.common.RepositoryBase;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChangeRequestRepository extends RepositoryBase<ChangeRequest, String>,
        ReadOnlyPageableRepository<ChangeRequest, String>,
        JpaSpecificationExecutor<ChangeRequest> {
}
