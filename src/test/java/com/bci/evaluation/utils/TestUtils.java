package com.bci.evaluation.utils;

import com.bci.evaluation.dto.AuthenticationResponse;
import com.bci.evaluation.dto.UserAuthentication;
import com.bci.evaluation.dto.UserRequest;
import com.bci.evaluation.dto.UserResponse;
import com.bci.evaluation.model.User;

import java.util.ArrayList;

public class TestUtils {

  public static final String TOKEN = "token";
  public static final String USERNAME = "username@dominio.cl";
  public static final String USERNAME_1 = "username@mail.com";
  public static final String PASSWORD = "Password1!";
  public static final String PASSWORD_1 = "password";
  public static final String MESSAGE = "message";
  public static final String EMAIL_REGEXP = "^[a-z0-9](\\.?[a-z0-9]){5,}@dominio\\.cl$";
  public static final String PASSWORD_REGEXP = "^(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*[@!#$%^&+=]).*$";
  public static final String UUID = "123e4567-e89b-12d3-a456-426614174000";

  public static UserAuthentication getUserAuthentication() {
    return new UserAuthentication(USERNAME, PASSWORD);
  }

  public static UserRequest getUserRequest() {
    return UserRequest.builder().email(USERNAME).password(PASSWORD).phones(new ArrayList<>()).build();
  }

  public static UserRequest getUserRequest(String username, String password) {
    return UserRequest.builder().email(username).password(password).phones(new ArrayList<>()).build();
  }

  public static UserResponse getUserResponse() {
    return UserResponse.builder().id(UUID).token(TOKEN).build();
  }

  public static User getUser() {
    return User.builder().email(USERNAME).password(PASSWORD).uuid(java.util.UUID.fromString(UUID))
        .phones(new ArrayList<>()).build();
  }

  public static User getUserWithToken() {
    return User.builder().email(USERNAME).password(PASSWORD).token(TOKEN).uuid(java.util.UUID.fromString(UUID))
        .phones(new ArrayList<>()).build();
  }

  public static User getUserWrongUsername() {
    return User.builder().email(USERNAME_1).password(PASSWORD).build();
  }

  public static org.springframework.security.core.userdetails.User getSecurityUser() {
    return new org.springframework.security.core.userdetails.User(TestUtils.USERNAME, TestUtils.PASSWORD,
        new ArrayList<>());
  }

  public static AuthenticationResponse getAuthenticationResponse() {
    return new AuthenticationResponse(TOKEN);
  }

}
