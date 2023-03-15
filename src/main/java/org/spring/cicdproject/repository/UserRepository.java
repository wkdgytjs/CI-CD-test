package org.spring.cicdproject.repository;

import org.spring.cicdproject.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
