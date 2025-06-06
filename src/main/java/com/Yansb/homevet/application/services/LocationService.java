package com.Yansb.homevet.application.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Yansb.homevet.api.location.response.NearbyDoctorResponse;
import com.Yansb.homevet.infrastructure.entities.DoctorEntity;
import com.Yansb.homevet.infrastructure.entities.SpecialtyEntity;
import com.Yansb.homevet.infrastructure.repositories.DoctorRepository;

@Service
public class LocationService {

  DoctorRepository doctorRepository;

  public LocationService(DoctorRepository doctorRepository) {
    this.doctorRepository = doctorRepository;
  }

  public List<NearbyDoctorResponse> getNearbyDoctors(double lat, double lng) {
    return this.doctorRepository.findDoctorsWithinRange(lat, lng)
        .stream()
        .map(this::mapToNearbyDoctorResponse)
        .collect(Collectors.toList());
  }

  private NearbyDoctorResponse mapToNearbyDoctorResponse(DoctorEntity doctor) {
    var user = doctor.getUser();
    var specialties = doctor.getSpecialties() != null
        ? doctor.getSpecialties().stream()
            .map(SpecialtyEntity::getName)
            .collect(Collectors.toList())
        : List.<String>of();

    return new NearbyDoctorResponse(
        doctor.getId(),
        doctor.getLicense_number(),
        doctor.getService_radius(),
        user.getName(),
        user.getEmail(),
        user.getPhoneNumber(),
        user.getProfilePicture(),
        specialties);
  }
}
