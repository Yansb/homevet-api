package com.Yansb.homevet.infrastructure.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.Yansb.homevet.infrastructure.entities.DoctorEntity;

public interface DoctorRepository extends CrudRepository<DoctorEntity, UUID> {

}
