package com.Yansb.homevet.application.services;

import java.util.List;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.Yansb.homevet.api.pets.responses.ListUserPetsResponse;
import com.Yansb.homevet.infrastructure.entities.PetsEntity;
import com.Yansb.homevet.infrastructure.repositories.PetsRepository;
import com.Yansb.homevet.infrastructure.repositories.UserRepository;

@Service
public class PetsService {
  PetsRepository petsRepository;
  UserRepository userRepository;

  public PetsService(PetsRepository petsRepository, UserRepository userRepository) {
    this.petsRepository = petsRepository;
    this.userRepository = userRepository;
  }

  public List<ListUserPetsResponse> listUserPets(String userId) {
    List<PetsEntity> pets = petsRepository.findByOwnerId(userId);
    return ListUserPetsResponse.fromEntity(pets);
  }

  @Transactional
  public void createPet(PetsEntity pet, String ownerId) {
    var user = userRepository.findById(ownerId).orElseThrow();
    var createdPet = petsRepository.save(pet);
    user.addPet(createdPet);
    userRepository.save(user);
  }
}
