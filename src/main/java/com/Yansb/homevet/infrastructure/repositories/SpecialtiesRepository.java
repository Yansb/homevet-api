package com.Yansb.homevet.infrastructure.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.Yansb.homevet.infrastructure.entities.SpecialtyEntity;

public interface SpecialtiesRepository extends CrudRepository<SpecialtyEntity, UUID> {

}
