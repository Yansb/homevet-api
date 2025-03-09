package com.Yansb.homevet.infrastructure.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

  @Bean
  public FirebaseApp firebaseApp() throws IOException {
    if (!FirebaseApp.getApps().isEmpty()){
      return null;
    }
    String firebaseConfig = System.getenv("FIREBASE_CREDENTIALS");
    InputStream serviceAccount = new ByteArrayInputStream(firebaseConfig.getBytes(StandardCharsets.UTF_8));

    var firebaseOptions = FirebaseOptions.builder()
      .setCredentials(GoogleCredentials.fromStream(serviceAccount))
      .build();

    return FirebaseApp.initializeApp(firebaseOptions);
  }
}
