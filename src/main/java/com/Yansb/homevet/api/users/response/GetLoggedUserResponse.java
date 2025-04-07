package com.Yansb.homevet.api.users.response;

import java.util.List;

import com.Yansb.homevet.infrastructure.entities.FileEntity;
import com.Yansb.homevet.infrastructure.entities.UserEntity;
import com.Yansb.homevet.infrastructure.entities.enums.UserRole;
import com.Yansb.homevet.infrastructure.serializers.ProfilePictureSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public record GetLoggedUserResponse(String id,
                String name,
                String email,
                String phoneNumber,
                @JsonSerialize(using = ProfilePictureSerializer.class) FileEntity profilePicture,
                List<UserRole> roles) {
        public static GetLoggedUserResponse fromEntity(UserEntity userEntity) {
                return new GetLoggedUserResponse(
                                userEntity.getId(),
                                userEntity.getName(),
                                userEntity.getEmail(),
                                userEntity.getPhoneNumber(),
                                userEntity.getProfilePicture(),
                                userEntity.getRoles());
        }

}
