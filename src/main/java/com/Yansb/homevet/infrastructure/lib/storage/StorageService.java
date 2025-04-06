package com.Yansb.homevet.infrastructure.lib.storage;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
public class StorageService {

  @Autowired
  private S3Presigner presigner;

  @Value("${cloud.aws.s3.bucket}")
  private String bucketName;

  @Value("${cloud.aws.region.static}")
  private String region;

  public String createPresignedUrl(String keyName) {
    Map<String, String> metadata = Map.of(
        "Access-Control-Allow-Origin", "*",
        "Access-Control-Allow-Methods", "GET,PUT,POST,DELETE",
        "Access-Control-Allow-Headers", "*");

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
    return presignedRequest.url().toExternalForm();
  }

  public String getPresignedUrl(String keyName) {
    return presigner.presignGetObject(presignRequest -> presignRequest
        .signatureDuration(Duration.ofMinutes(10))
        .getObjectRequest(getObjectRequest -> getObjectRequest
            .bucket(bucketName)
            .key(keyName)))
        .url()
        .toExternalForm();
  }
}
