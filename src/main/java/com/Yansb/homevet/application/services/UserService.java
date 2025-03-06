package com.Yansb.homevet.application.services;

import com.Yansb.homevet.api.users.request.CreateUserRequest;
import com.Yansb.homevet.api.exceptions.CreateUserException;
import com.Yansb.homevet.infrastructure.entities.AddressEntity;
import com.Yansb.homevet.infrastructure.entities.UserEntity;
import com.Yansb.homevet.infrastructure.entities.UserRole;
import com.Yansb.homevet.infrastructure.firebase.FirebaseService;
import com.Yansb.homevet.infrastructure.repositories.UserRepository;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final FirebaseService firebaseService;

    public UserService(UserRepository userRepository, FirebaseService firebaseService){
        this.userRepository = userRepository;
        this.firebaseService = firebaseService;
    }

    public String CreateUser(CreateUserRequest createUserRequest) {
        try {
            final var firebaseUser = firebaseService.createUser(createUserRequest);
            var userEntity = mapToUserEntity(createUserRequest, firebaseUser.getUid());

            userRepository.save(userEntity);

            return userEntity.getId();
        } catch (Exception e) {
            throw new CreateUserException(e.getMessage());
        }
    }


    private UserEntity mapToUserEntity(CreateUserRequest createUserRequest, String firebaseId) {
        return UserEntity.builder()
                .id(firebaseId)
                .name(createUserRequest.firstName() + " " + createUserRequest.lastName())
                .email(createUserRequest.email())
                .phoneNumber(createUserRequest.phoneNumber())
                .roles(List.of(UserRole.USER))
                .address(Set.of(
                        AddressEntity.builder()
                                .id(UUID.randomUUID())
                                .street(createUserRequest.address().street())
                                .city(createUserRequest.address().city())
                                .number(createUserRequest.address().number())
                                .state(createUserRequest.address().state())
                                .zipCode(createUserRequest.address().zipCode())
                                .addressName(createUserRequest.address().addressName())
                                .complement(createUserRequest.address().complement())
                                .mainAddressAt(Instant.now())
                                .user(UserEntity.builder().id(firebaseId).build())
                                .build()))
                .build();
    }

}
