package com.Yansb.homevet.application.services;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Yansb.homevet.api.doctors.request.CreateDoctorRequest;
import com.Yansb.homevet.api.exceptions.CreateUserException;
import com.Yansb.homevet.infrastructure.entities.AddressEntity;
import com.Yansb.homevet.infrastructure.entities.DoctorEntity;
import com.Yansb.homevet.infrastructure.entities.UserEntity;
import com.Yansb.homevet.infrastructure.entities.UserRole;
import com.Yansb.homevet.infrastructure.firebase.FirebaseService;
import com.Yansb.homevet.infrastructure.repositories.DoctorRepository;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

@Service
public class DoctorService {
  private final DoctorRepository doctorRepository;
  private final FirebaseService firebaseService;

  public DoctorService(FirebaseService firebaseService,
      DoctorRepository doctorRepository) {
    this.firebaseService = firebaseService;
    this.doctorRepository = doctorRepository;
  }

  @Transactional
  public String CreateDoctor(CreateDoctorRequest request) throws FirebaseAuthException {
    UserRecord firebaseUser = null;
    try {
      firebaseUser = firebaseService.createDoctor(request);
      DoctorEntity doctorEntity = this.mapToDoctorEntity(request, firebaseUser.getUid());
      var savedDoctor = this.doctorRepository.save(doctorEntity);

      return savedDoctor.getId().toString();
    } catch (Exception e) {
      if (firebaseUser != null) {
        firebaseService.deleteUser(firebaseUser.getUid());
      }

      throw new CreateUserException(e.getMessage());
    }
  }

  private DoctorEntity mapToDoctorEntity(CreateDoctorRequest createDoctorRequest, String firebaseId) {
    var point = createDoctorRequest.address().location() != null
        ? new GeometryFactory().createPoint(new Coordinate(createDoctorRequest.address().location().latitude(),
            createDoctorRequest.address().location().longitude()))
        : null;

    var addressEntity = AddressEntity.builder()
        .id(UUID.randomUUID())
        .street(createDoctorRequest.address().street())
        .city(createDoctorRequest.address().city())
        .number(createDoctorRequest.address().number())
        .state(createDoctorRequest.address().state())
        .zipCode(createDoctorRequest.address().zipCode())
        .addressName(createDoctorRequest.address().addressName())
        .complement(createDoctorRequest.address().complement())
        .mainAddressAt(Instant.now())
        .location(point)
        .user(UserEntity.builder().id(firebaseId).build())
        .build();

    var userEntity = UserEntity.builder()
        .id(firebaseId)
        .name(createDoctorRequest.firstName() + " " + createDoctorRequest.lastName())
        .email(createDoctorRequest.email())
        .phoneNumber(createDoctorRequest.phoneNumber())
        .roles(List.of(UserRole.USER, UserRole.VETERINARIAN))
        .address(Set.of(addressEntity))
        .build();

    var attendingAddress = createDoctorRequest.isAttendingAddressSameAsAddress() != null
        && createDoctorRequest.isAttendingAddressSameAsAddress()
            ? addressEntity
            : AddressEntity.builder()
                .id(UUID.randomUUID())
                .street(createDoctorRequest.attendingAddress().street())
                .city(createDoctorRequest.attendingAddress().city())
                .number(createDoctorRequest.attendingAddress().number())
                .state(createDoctorRequest.attendingAddress().state())
                .zipCode(createDoctorRequest.attendingAddress().zipCode())
                .addressName(createDoctorRequest.attendingAddress().addressName())
                .complement(createDoctorRequest.attendingAddress().complement())
                .location(createDoctorRequest.attendingAddress().location() != null
                    ? new GeometryFactory().createPoint(new Coordinate(
                        createDoctorRequest.attendingAddress().location().latitude(),
                        createDoctorRequest.attendingAddress().location().longitude()))
                    : null)
                .build();

    return DoctorEntity.builder()
        .id(UUID.randomUUID())
        .license_number(createDoctorRequest.licenseNumber())
        .service_radius(createDoctorRequest.radius())
        .user(userEntity)
        .attendingAddress(attendingAddress)
        .build();
  }
}
