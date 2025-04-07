package com.Yansb.homevet.api.pets;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Yansb.homevet.api.pets.requests.CreatePetRequest;
import com.Yansb.homevet.api.pets.responses.ListUserPetsResponse;
import com.Yansb.homevet.application.services.PetsService;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/pets")
public class PetsController {

  private final PetsService petsService;

  public PetsController(PetsService petsService) {
    this.petsService = petsService;
  }

  @GetMapping()
  public List<ListUserPetsResponse> getMethodName(@AuthenticationPrincipal Jwt principal) {
    return petsService.listUserPets(principal.getSubject());
  }

  @PostMapping
  public void postMethodName(@Validated @RequestBody CreatePetRequest pet,
      @AuthenticationPrincipal Jwt principal) {
    String userId = principal.getSubject();
    petsService.createPet(pet.toEntity(), userId);
  }

}
