package com.bci.evaluation.controller;

import com.bci.evaluation.dto.AuthenticationResponse;
import com.bci.evaluation.dto.ErrorResponse;
import com.bci.evaluation.dto.UserAuthentication;
import com.bci.evaluation.dto.UserRequest;
import com.bci.evaluation.dto.UserResponse;
import com.bci.evaluation.service.UserAuthenticationService;
import com.bci.evaluation.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserAuthenticationService userAuthenticationService;

  @Operation(summary = "Authenticate a user and return their token")
  @PostMapping("/authentication")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User authenticated and token returned",
          content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = AuthenticationResponse.class)) }),
      @ApiResponse(responseCode = "401", description = "Invalid credentials",
          content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)) }),
      @ApiResponse(responseCode = "403", description = "The user is disabled",
          content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)) })
  })
  public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
      @RequestBody UserAuthentication userAuthenticationRequest) {

    return ResponseEntity.ok(this.userAuthenticationService.authenticate(userAuthenticationRequest.getUsername(),
        userAuthenticationRequest.getPassword()));
  }

  @Operation(summary = "Create a new user and authenticates  them")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User created and authenticated",
          content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = UserResponse.class)) }),
      @ApiResponse(responseCode = "400", description = "User already exists or invalid email or password",
          content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)) })
  })
  @PostMapping("/user")
  public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.userAuthenticationService.firstTimeAuthenticate(userRequest));
  }

  @Operation(summary = "Find a user by email and return their information")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User found and returned",
          content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = UserResponse.class)) }),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)) })
  })
  @GetMapping("/user/{email}")
  public ResponseEntity<UserResponse> findUser(@PathVariable String email) {

    return ResponseEntity.ok(this.userService.findUserResponse(email));
  }

  @Operation(summary = "Delete a user by email")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "User deleted"),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)) })
  })
  @DeleteMapping("/user/{email}")
  public ResponseEntity<Void> deleteUser(@PathVariable String email) {
    this.userService.deleteUser(email);

    return ResponseEntity.noContent().build();
  }
}
