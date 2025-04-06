package com.Yansb.homevet.api.location.response;

import java.util.List;
import java.util.UUID;

import com.Yansb.homevet.infrastructure.entities.FileEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.Yansb.homevet.infrastructure.serializers.ProfilePictureSerializer;

public record NearbyDoctorResponse(
        UUID id,
        String licenseNumber,
        Integer serviceRadius,
        String name,
        String email,
        String phoneNumber,
        @JsonSerialize(using = ProfilePictureSerializer.class) FileEntity profilePicture,
        List<String> specialties) {
}
