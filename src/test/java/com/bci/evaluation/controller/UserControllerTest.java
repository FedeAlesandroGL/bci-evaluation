package com.bci.evaluation.controller;

import com.bci.evaluation.service.UserAuthenticationService;
import com.bci.evaluation.service.UserService;
import com.bci.evaluation.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  UserService userService;
  UserAuthenticationService userAuthenticationService;
  UserController userController;

  @BeforeEach
  void setUp() {
    userService = mock(UserService.class);
    userAuthenticationService = mock(UserAuthenticationService.class);
    userController = new UserController(userService, userAuthenticationService);
  }

  @Test
  void whenCreateAuthenticationTokenThenOk() {

    when(this.userAuthenticationService.authenticate(TestUtils.USERNAME, TestUtils.PASSWORD)).thenReturn(
        TestUtils.getAuthenticationResponse());

    assertEquals(ResponseEntity.ok(TestUtils.getAuthenticationResponse()),
        this.userController.createAuthenticationToken(TestUtils.getUserAuthentication()));
  }

  @Test
  void whenCreateUserThenCreated() {

    when(this.userService.saveUser(TestUtils.getUserRequest())).thenReturn(
        TestUtils.getUserResponse());

    assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(TestUtils.getUserResponse()),
        this.userController.createUser(TestUtils.getUserRequest()));
  }

  @Test
  void whenFindUserThenOk() {

    when(this.userService.findUserResponse(TestUtils.USERNAME)).thenReturn(TestUtils.getUserResponse());

    assertEquals(ResponseEntity.ok(TestUtils.getUserResponse()),
        this.userController.findUser(TestUtils.USERNAME));
  }

  @Test
  void whenDeleteUserThenNoContent() {

    assertEquals(ResponseEntity.noContent().build(),
        this.userController.deleteUser(TestUtils.USERNAME));
  }
}
