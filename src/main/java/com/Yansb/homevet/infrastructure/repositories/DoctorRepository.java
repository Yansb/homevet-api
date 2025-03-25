package com.Yansb.homevet.infrastructure.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Yansb.homevet.infrastructure.entities.DoctorEntity;

@Repository
public interface DoctorRepository extends CrudRepository<DoctorEntity, UUID> {

  @Query(value = """
      SELECT d.*
      FROM doctors d
      JOIN address a ON d.attending_address_id = a.id
      WHERE ST_DWithin(
        a.location,
        ST_SetSRID(ST_MakePoint(:userLng, :userLat), 4326)::geography,
        d.service_radius
      )
      """, nativeQuery = true)
  public List<DoctorEntity> findDoctorsWithinRange(@Param("userLat") double userLat, @Param("userLng") double userLng);

  public DoctorEntity findByUserId(String userId);
}
