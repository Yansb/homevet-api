package com.Yansb.homevet.api.doctors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

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

}
