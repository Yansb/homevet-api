package com.Yansb.homevet.infrastructure.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.Yansb.homevet.infrastructure.entities.FileEntity;

public interface FileRepository extends CrudRepository<FileEntity, String> {

  @Query("SELECT f FROM FileEntity f WHERE f.id = :id AND f.uploadedAt IS NOT NULL")
  Optional<FileEntity> findByIdIgnoringNullUploadedAt(@Param("id") String id);
}
