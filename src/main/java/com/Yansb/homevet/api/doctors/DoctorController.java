package com.Yansb.homevet.api.doctors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import com.Yansb.homevet.api.doctors.request.AddDoctorSpecialtyRequest;
import com.Yansb.homevet.api.doctors.request.CreateDoctorRequest;
import com.Yansb.homevet.application.services.DoctorService;
import com.google.firebase.auth.FirebaseAuthException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
  private final DoctorService doctorService;

  public DoctorController(DoctorService doctorService) {
    this.doctorService = doctorService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public String postMethodName(@Validated @RequestBody CreateDoctorRequest request) throws FirebaseAuthException {
    return this.doctorService.CreateDoctor(request);
  }

  @PostMapping("/specialty")
  @PreAuthorize("hasAuthority('VETERINARIAN')")
  public String addNewSpecialty(@RequestBody AddDoctorSpecialtyRequest request,
      @AuthenticationPrincipal Jwt principal) {
    String userId = principal.getSubject();
    return this.doctorService.addDoctorSpecialty(request.specialtyId(), userId);
  }

}
