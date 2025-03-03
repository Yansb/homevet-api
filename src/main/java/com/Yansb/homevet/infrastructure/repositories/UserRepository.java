package com.Yansb.homevet.infrastructure.repositories;

import com.Yansb.homevet.infrastructure.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String> {
}
