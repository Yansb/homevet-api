package com.Yansb.homevet.api.pets.responses;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.Yansb.homevet.infrastructure.entities.PetsEntity;

public record ListUserPetsResponse(
        UUID id,
        String name,
        String species,
        String breed) {

    public static ListUserPetsResponse fromEntity(PetsEntity pet) {
        return new ListUserPetsResponse(
                pet.getId(),
                pet.getName(),
                pet.getSpecies(),
                pet.getBreed());
    }

    public static List<ListUserPetsResponse> fromEntity(Collection<PetsEntity> pets) {
        return pets.stream()
                .map(ListUserPetsResponse::fromEntity)
                .toList();
    }

}
