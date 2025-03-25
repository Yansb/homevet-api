package com.Yansb.homevet.api.admin.specialties;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Yansb.homevet.api.admin.specialties.request.AddSpecialtyRequest;
import com.Yansb.homevet.infrastructure.entities.SpecialtyEntity;
import com.Yansb.homevet.infrastructure.repositories.SpecialtiesRepository;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/admin/specialty")
@PreAuthorize("hasAuthority('ADMIN')")
public class SpecialtiesController {

  SpecialtiesRepository specialtiesRepository;

  public SpecialtiesController(SpecialtiesRepository specialtiesRepository) {
    this.specialtiesRepository = specialtiesRepository;
  }

  @PostMapping
  public Map<String, UUID> addSpecialty(@RequestBody AddSpecialtyRequest request) {
    final var newSpecialty = SpecialtyEntity.builder()
        .id(UUID.randomUUID())
        .name(request.name())
        .build();

    this.specialtiesRepository.save(newSpecialty);

    return Map.of("id", newSpecialty.getId());
  }
}
