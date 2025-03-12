package com.Yansb.homevet.api.users.request;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequestAddress(
    @NotBlank String street,
    @NotBlank String city,
    @NotBlank String state,
    @NotBlank String zipCode,
    @NotBlank String addressName,
    @NotBlank String number,
    String complement,
    CreateUserRequestAddressLocation location
) {}