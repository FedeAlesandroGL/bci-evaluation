package com.bci.evaluation.controller;

import com.bci.evaluation.dto.UserAuthentication;
import com.bci.evaluation.dto.UserRequest;
import com.bci.evaluation.dto.UserResponse;
import com.bci.evaluation.service.UserAuthenticationService;
import com.bci.evaluation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserAuthenticationService userAuthenticationService;

  @PostMapping("/authentication")
  public ResponseEntity<String> createAuthenticationToken(UserAuthentication userAuthenticationRequest) {

    return ResponseEntity.ok(this.userAuthenticationService.authenticate(userAuthenticationRequest.getUsername(),
        userAuthenticationRequest.getPassword()));
  }

  @PostMapping("/user")
  public ResponseEntity<UserResponse> createUser(UserRequest userRequest) {

    return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.saveUser(userRequest));
  }

  @GetMapping("/user/{email}")
  public ResponseEntity<UserResponse> findUser(@PathVariable String email) {

    return ResponseEntity.ok(this.userService.findUserResponse(email));
  }

  @DeleteMapping("/user/{email}")
  public ResponseEntity<Void> deleteUser(@PathVariable String email) {
    this.userService.deleteUser(email);

    return ResponseEntity.noContent().build();
  }
}
