package com.Yansb.homevet.api.admin.specialties.request;

import com.google.firebase.database.annotations.NotNull;

import jakarta.validation.constraints.NotBlank;

public record AddSpecialtyRequest(
    @NotBlank @NotNull String name) {

}
