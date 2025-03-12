package com.Yansb.homevet.api.users;

import com.Yansb.homevet.api.users.request.CreateUserRequest;
import com.Yansb.homevet.api.users.response.CreateUserResponse;
import com.Yansb.homevet.application.services.UserService;
import com.Yansb.homevet.infrastructure.entities.UserEntity;
import com.Yansb.homevet.infrastructure.repositories.UserRepository;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/me")
    public Optional<UserEntity> getUser(@AuthenticationPrincipal Jwt user) {

        return this.userRepository.findById(user.getSubject());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) throws FirebaseAuthException {
        var userId = userService.CreateUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CreateUserResponse(userId));
    }
}
