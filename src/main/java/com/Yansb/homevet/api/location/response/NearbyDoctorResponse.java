package com.Yansb.homevet.api.location.response;

import java.util.List;
import java.util.UUID;

public record NearbyDoctorResponse(
    UUID id,
    String licenseNumber,
    Integer serviceRadius,
    String name,
    String email,
    String phoneNumber,
    List<String> specialties) {
}
