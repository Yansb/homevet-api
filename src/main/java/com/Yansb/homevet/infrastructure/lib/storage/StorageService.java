package com.Yansb.homevet.infrastructure.lib.storage;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
public class StorageService {

  @Autowired
  S3AsyncClient s3AsyncClient;

  @Value("${cloud.aws.s3.bucket}")
  private String bucketName;

  public String createPresignedUrl(String bucketName, String keyName, Map<String, String> metadata) {
    try (S3Presigner presigner = S3Presigner.create()) {

      PutObjectRequest objectRequest = PutObjectRequest.builder()
          .bucket(bucketName)
          .key(keyName)
          .metadata(metadata)
          .build();

      PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
          .signatureDuration(Duration.ofMinutes(10))
          .putObjectRequest(objectRequest)
          .build();

      PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
      String myURL = presignedRequest.url().toString();

      return presignedRequest.url().toExternalForm();
    }
  }
}
