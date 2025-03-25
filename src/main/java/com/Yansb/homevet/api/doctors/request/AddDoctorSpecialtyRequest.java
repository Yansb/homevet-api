package com.Yansb.homevet.api.doctors.request;

import java.util.UUID;

import com.google.firebase.database.annotations.NotNull;

import jakarta.validation.constraints.NotBlank;

public record AddDoctorSpecialtyRequest(
                @NotNull @NotBlank(message = "A especialidade precisa ser descrita") UUID specialtyId) {

}
