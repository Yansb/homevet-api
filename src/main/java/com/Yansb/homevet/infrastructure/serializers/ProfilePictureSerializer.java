package com.Yansb.homevet.infrastructure.serializers;

import org.springframework.beans.factory.annotation.Autowired;

import com.Yansb.homevet.infrastructure.entities.FileEntity;
import com.Yansb.homevet.infrastructure.lib.storage.StorageService;
import com.fasterxml.jackson.databind.JsonSerializer;

public class ProfilePictureSerializer extends JsonSerializer<FileEntity> {
  @Autowired
  StorageService storageService;

  @Override
  public void serialize(FileEntity value, com.fasterxml.jackson.core.JsonGenerator gen,
      com.fasterxml.jackson.databind.SerializerProvider serializers) throws java.io.IOException {
    if (value == null) {
      gen.writeNull();
    } else {
      gen.writeString(storageService.getPresignedUrl(value.getId()));
    }
  }

}
