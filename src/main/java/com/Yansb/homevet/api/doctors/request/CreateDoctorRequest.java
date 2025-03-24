package com.Yansb.homevet.api.doctors.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

public record CreateDoctorRequest(
    @Email(message = "Invalid email format") @NotBlank(message = "Email is required") String email,

    @NotBlank(message = "Password is required") @Size(min = 8, message = "Password must be at least 8 characters long") String password,

    @NotBlank(message = "First name is required") @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters") String firstName,

    @NotBlank(message = "Last name is required") @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters") String lastName,

    @NotBlank(message = "Phone number is required") @Size(min = 12, max = 15, message = "Phone number must be between 12 and 15 characters") String phoneNumber,

    @NotNull(message = "Address is required") CreateDoctorRequestAddress address,

    @NotNull(message = "radius is required") @Min(value = 1, message = "Service radius must be at least 1 km") @Max(value = 100, message = "Service radius cannot exceed 100 km") Integer radius,

    Boolean isAttendingAddressSameAsAddress,

    @NotBlank(message = "License number is required") @Size(min = 5, max = 20, message = "License number must be between 5 and 20 characters") String licenseNumber,

    CreateDoctorRequestAddress attendingAddress) {

  public record CreateDoctorRequestAddress(
      @NotBlank(message = "Street is required") @Size(max = 100, message = "Street cannot exceed 100 characters") String street,

      @NotBlank(message = "City is required") @Size(max = 50, message = "City cannot exceed 50 characters") String city,

      @NotBlank(message = "State is required") @Size(max = 2, message = "State must be 2 characters") String state,

      @NotBlank(message = "ZIP code is required") @Size(min = 8, max = 8, message = "ZIP code must be 8 characters") String zipCode,

      @NotBlank(message = "Address name is required") @Size(max = 50, message = "Address name cannot exceed 50 characters") String addressName,

      @NotBlank(message = "Number is required") @Size(max = 10, message = "Number cannot exceed 10 characters") String number,

      @Size(max = 100, message = "Complement cannot exceed 100 characters") String complement,

      CreateDoctorRequestAddressLocation location) {

    public record CreateDoctorRequestAddressLocation(
        @Min(value = -90, message = "Latitude must be between -90 and 90") @Max(value = 90, message = "Latitude must be between -90 and 90") double latitude,

        @Min(value = -180, message = "Longitude must be between -180 and 180") @Max(value = 180, message = "Longitude must be between -180 and 180") double longitude) {
    }
  }
}
