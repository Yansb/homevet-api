package com.Yansb.homevet.api.users;

import com.Yansb.homevet.api.users.request.CreateUserRequest;
import com.Yansb.homevet.api.users.request.UploadImageRequest;
import com.Yansb.homevet.api.users.response.CreateUserResponse;
import com.Yansb.homevet.api.users.response.GetLoggedUserResponse;
import com.Yansb.homevet.application.dtos.CreateSignedUrlOutput;
import com.Yansb.homevet.application.services.UserService;
import com.Yansb.homevet.infrastructure.lib.storage.StorageService;
import com.Yansb.homevet.infrastructure.repositories.UserRepository;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService, StorageService storageService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/me")
    public GetLoggedUserResponse getUser(@AuthenticationPrincipal Jwt user) {
        return this.userService.getLoggedUser(user.getSubject());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request)
            throws FirebaseAuthException {
        var userId = userService.createUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CreateUserResponse(userId));
    }

    @PostMapping("upload-image")
    public CreateSignedUrlOutput putMethodName(@RequestBody UploadImageRequest request,
            @AuthenticationPrincipal Jwt user) {

        return this.userService.createUrlToUploadProfilePicture(request.fileName(), request.fileType(),
                user.getSubject());
    }

    @PutMapping("confirm-profile/{key}")
    public void confirmProfilePicUpload(@PathVariable("key") String key) {

        this.userService.confirmProfilePicUpload(key);
    }
}
