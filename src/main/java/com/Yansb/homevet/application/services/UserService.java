package com.Yansb.homevet.application.services;

import com.Yansb.homevet.api.users.request.CreateUserRequest;
import com.Yansb.homevet.api.users.response.GetLoggedUserResponse;
import com.Yansb.homevet.application.dtos.CreateSignedUrlOutput;
import com.Yansb.homevet.api.exceptions.CreateUserException;
import com.Yansb.homevet.infrastructure.entities.AddressEntity;
import com.Yansb.homevet.infrastructure.entities.FileEntity;
import com.Yansb.homevet.infrastructure.entities.UserEntity;
import com.Yansb.homevet.infrastructure.entities.enums.UserRole;
import com.Yansb.homevet.infrastructure.firebase.FirebaseService;
import com.Yansb.homevet.infrastructure.lib.storage.StorageService;
import com.Yansb.homevet.infrastructure.repositories.FileRepository;
import com.Yansb.homevet.infrastructure.repositories.UserRepository;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import jakarta.transaction.Transactional;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final FirebaseService firebaseService;
    private final StorageService storageService;

    public UserService(UserRepository userRepository, FirebaseService firebaseService, StorageService storageService,
            FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.firebaseService = firebaseService;
        this.storageService = storageService;
        this.fileRepository = fileRepository;
    }

    public GetLoggedUserResponse getLoggedUser(String id) {
        var userEntity = this.userRepository.findById(id).orElseThrow();
        return GetLoggedUserResponse.fromEntity(userEntity);
    }

    public String createUser(CreateUserRequest createUserRequest) throws FirebaseAuthException {
        UserRecord firebaseUser = null;
        try {
            firebaseUser = firebaseService.createUser(createUserRequest);
            var userEntity = mapToUserEntity(createUserRequest, firebaseUser.getUid());

            userRepository.save(userEntity);

            return userEntity.getId();
        } catch (Exception e) {
            if (firebaseUser != null) {
                firebaseService.deleteUser(firebaseUser.getUid());
            }

            throw new CreateUserException(e.getMessage());
        }
    }

    public CreateSignedUrlOutput createUrlToUploadProfilePicture(String fileName, String fileType, String userId) {
        var key = fileName + Instant.now().toEpochMilli();
        var presignedUrl = storageService.createPresignedUrl(key);
        var fileEntity = FileEntity.builder()
                .id(key)
                .name(fileName)
                .file_type(fileType)
                .link(presignedUrl)
                .user(UserEntity.builder().id(userId).build())
                .build();
        this.fileRepository.save(fileEntity);

        return new CreateSignedUrlOutput(presignedUrl, key);
    }

    @Transactional
    public void confirmProfilePicUpload(String key) {
        var fileEntity = this.fileRepository.findById(key).orElseThrow();
        fileEntity.setUploadedAt(Instant.now());
        var userEntity = fileEntity.getUser();
        userEntity.setProfilePicture(fileEntity);

        fileRepository.save(fileEntity);
        userRepository.save(userEntity);

    }

    private UserEntity mapToUserEntity(CreateUserRequest createUserRequest, String firebaseId) {
        var point = createUserRequest.address().location() != null
                ? new GeometryFactory().createPoint(new Coordinate(createUserRequest.address().location().longitude(),
                        createUserRequest.address().location().latitude()))
                : null;

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
                                .location(point)
                                .user(UserEntity.builder().id(firebaseId).build())
                                .build()))
                .build();
    }

}
