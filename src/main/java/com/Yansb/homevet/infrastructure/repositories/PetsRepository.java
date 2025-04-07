package com.Yansb.homevet.infrastructure.repositories;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Yansb.homevet.infrastructure.entities.PetsEntity;

public interface PetsRepository extends JpaRepository<PetsEntity, UUID> {
  @Query("SELECT p FROM PetsEntity p JOIN p.owners o WHERE o.id = :ownerId")
  List<PetsEntity> findByOwnerId(@Param("ownerId") String ownerId);
}
