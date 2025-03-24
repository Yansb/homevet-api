package com.Yansb.homevet.infrastructure.firebase;

import com.Yansb.homevet.api.doctors.request.CreateDoctorRequest;
import com.Yansb.homevet.api.users.request.CreateUserRequest;
import com.Yansb.homevet.infrastructure.entities.UserRole;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class FirebaseService {

  public UserRecord createUser(CreateUserRequest createUserRequest) throws FirebaseAuthException {
    final var displayName = createUserRequest.firstName() + " " + createUserRequest.lastName();

    UserRecord.CreateRequest request = new UserRecord.CreateRequest()
        .setEmail(createUserRequest.email())
        .setDisplayName(displayName)
        .setPassword(createUserRequest.password())
        .setPhoneNumber(createUserRequest.phoneNumber());

    final var createdUser = FirebaseAuth.getInstance().createUser(request);

    assignRole(createdUser.getUid(), Set.of(UserRole.USER.name()));

    return createdUser;
  }

  public UserRecord createDoctor(CreateDoctorRequest createDoctorRequest) throws FirebaseAuthException {
    final var displayName = createDoctorRequest.firstName() + " " + createDoctorRequest.lastName();

    UserRecord.CreateRequest request = new UserRecord.CreateRequest()
        .setEmail(createDoctorRequest.email())
        .setDisplayName(displayName)
        .setPassword(createDoctorRequest.password())
        .setPhoneNumber(createDoctorRequest.phoneNumber());

    final var createdUser = FirebaseAuth.getInstance().createUser(request);

    assignRole(createdUser.getUid(), Set.of(UserRole.USER.name(), UserRole.VETERINARIAN.name()));

    return createdUser;
  }

  public void assignRole(String uid, Set<String> rolesToAdd) throws FirebaseAuthException {
    final var user = FirebaseAuth.getInstance().getUser(uid);

    Map<String, Object> currentClaims = user.getCustomClaims();

    List<String> rolesOld = (List<String>) currentClaims.getOrDefault("authorities", List.of());
    Set<String> rolesNew = new HashSet<>(rolesOld);
    rolesNew.addAll(rolesToAdd);

    HashMap<String, Object> newClaims = new HashMap<>(currentClaims);
    newClaims.put("authorities", rolesNew);

    FirebaseAuth.getInstance().setCustomUserClaims(uid, newClaims);
  }

  public void deleteUser(String uid) throws FirebaseAuthException {
    FirebaseAuth.getInstance().deleteUser(uid);
  }
}
