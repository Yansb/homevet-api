package com.Yansb.homevet.infrastructure.repositories;

import com.Yansb.homevet.infrastructure.entities.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

}
