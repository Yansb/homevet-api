package com.Yansb.homevet.api.pets.requests;

import java.time.Instant;
import java.util.UUID;

import com.Yansb.homevet.infrastructure.entities.PetsEntity;
import com.Yansb.homevet.infrastructure.entities.enums.PetGender;
import com.Yansb.homevet.api.validation.EnumValidator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePetRequest(
    @NotBlank(message = "Name is required") String name,
    @NotBlank(message = "Species is required") String species,
    @NotBlank(message = "Breed is required") String breed,
    @NotNull(message = "Gender is required") @EnumValidator(enumClass = PetGender.class, message = "Invalid gender value") PetGender gender,
    Instant birthDate,
    Float weight,
    String notes) {
  public PetsEntity toEntity() {
    return PetsEntity.builder()
        .id(UUID.randomUUID())
        .name(name)
        .species(species)
        .breed(breed)
        .gender(gender)
        .birthDate(birthDate)
        .weight(weight)
        .notes(notes)
        .build();
  }

}
