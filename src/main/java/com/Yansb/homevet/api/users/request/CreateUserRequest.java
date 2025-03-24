package com.Yansb.homevet.api.users.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
                @Email String email,
                @NotBlank @Size(min = 8) String password,
                @NotBlank String firstName,
                @NotBlank String lastName,
                @NotBlank @Size(min = 12) String phoneNumber,
                @NotNull CreateUserRequestAddress address) {
        public record CreateUserRequestAddress(
                        @NotBlank String street,
                        @NotBlank String city,
                        @NotBlank String state,
                        @NotBlank String zipCode,
                        @NotBlank String addressName,
                        @NotBlank String number,
                        String complement,
                        CreateUserRequestAddressLocation location) {
                public record CreateUserRequestAddressLocation(
                                double latitude,
                                double longitude) {
                }
        }
}
