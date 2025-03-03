package com.Yansb.homevet.infrastructure.firebase;

import com.Yansb.homevet.api.users.request.CreateUserRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
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

    return FirebaseAuth.getInstance().createUser(request);
  }
}
