package com.bci.evaluation.service;

import com.bci.evaluation.dto.UserRequest;
import com.bci.evaluation.dto.UserResponse;
import com.bci.evaluation.exception.AlreadyExistsException;
import com.bci.evaluation.exception.BadRequestException;
import com.bci.evaluation.exception.NotFoundException;
import com.bci.evaluation.exception.RepositoryFailedException;
import com.bci.evaluation.model.User;
import com.bci.evaluation.repository.UserRepository;
import com.bci.evaluation.service.impl.UserServiceImpl;
import com.bci.evaluation.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  UserRepository userRepository;
  PasswordEncoder passwordEncoder;
  PhoneService phoneService;
  UserService userService;

  @BeforeEach
  void setUp() {
    this.userRepository = mock(UserRepository.class);
    this.passwordEncoder = mock(PasswordEncoder.class);
    this.phoneService = mock(PhoneService.class);
    this.userService = new UserServiceImpl(this.userRepository, this.passwordEncoder, phoneService);
  }

  @Test
  void whenSaveUserThenOk() {
    ReflectionTestUtils.setField(this.userService, "emailRegex", TestUtils.EMAIL_REGEXP);
    ReflectionTestUtils.setField(this.userService, "passwordRegex", TestUtils.PASSWORD_REGEXP);

    UserRequest userRequest = TestUtils.getUserRequest();

    when(this.userRepository.findByEmailAndIsActiveTrue(TestUtils.USERNAME)).thenReturn(Optional.empty());
    when(this.passwordEncoder.encode(TestUtils.PASSWORD)).thenReturn(TestUtils.PASSWORD);
    when(this.userRepository.save(any())).thenReturn(TestUtils.getUserWithToken());

    assertEquals(TestUtils.getUserResponse(), this.userService.saveUser(userRequest));
  }

  @Test
  void whenSaveUserThenThrowsAlreadyExistsException() {
    UserRequest userRequest = TestUtils.getUserRequest();

    when(this.userRepository.findByEmailAndIsActiveTrue(TestUtils.USERNAME)).thenReturn(
        Optional.of(TestUtils.getUser()));

    assertThrows(AlreadyExistsException.class, () -> this.userService.saveUser(userRequest));
  }

  @Test
  void whenSaveUserThenWrongUsernameThrowsBadRequestException() {
    ReflectionTestUtils.setField(this.userService, "emailRegex", TestUtils.EMAIL_REGEXP);
    ReflectionTestUtils.setField(this.userService, "passwordRegex", TestUtils.PASSWORD_REGEXP);

    UserRequest userRequest = TestUtils.getUserRequest(TestUtils.USERNAME_1, TestUtils.PASSWORD);

    when(this.userRepository.findByEmailAndIsActiveTrue(TestUtils.USERNAME_1)).thenReturn(Optional.empty());

    assertThrows(BadRequestException.class, () -> this.userService.saveUser(userRequest));
  }

  @Test
  void whenSaveUserThenWrongPasswordThrowsBadRequestException() {
    ReflectionTestUtils.setField(this.userService, "emailRegex", TestUtils.EMAIL_REGEXP);
    ReflectionTestUtils.setField(this.userService, "passwordRegex", TestUtils.PASSWORD_REGEXP);

    UserRequest userRequest = TestUtils.getUserRequest(TestUtils.USERNAME, TestUtils.PASSWORD_1);

    when(this.userRepository.findByEmailAndIsActiveTrue(TestUtils.USERNAME)).thenReturn(Optional.empty());

    assertThrows(BadRequestException.class, () -> this.userService.saveUser(userRequest));
  }

  @Test
  void whenSaveUserThenWrongUsernameAndPasswordThrowsBadRequestException() {
    ReflectionTestUtils.setField(this.userService, "emailRegex", TestUtils.EMAIL_REGEXP);
    ReflectionTestUtils.setField(this.userService, "passwordRegex", TestUtils.PASSWORD_REGEXP);

    UserRequest userRequest = TestUtils.getUserRequest(TestUtils.USERNAME_1, TestUtils.PASSWORD_1);

    when(this.userRepository.findByEmailAndIsActiveTrue(TestUtils.USERNAME_1)).thenReturn(Optional.empty());

    assertThrows(BadRequestException.class, () -> this.userService.saveUser(userRequest));
  }

  @Test
  void whenSaveUserThenThrowRepositoryFailedException() {
    ReflectionTestUtils.setField(this.userService, "emailRegex", TestUtils.EMAIL_REGEXP);
    ReflectionTestUtils.setField(this.userService, "passwordRegex", TestUtils.PASSWORD_REGEXP);

    UserRequest userRequest = TestUtils.getUserRequest();

    when(this.userRepository.findByEmailAndIsActiveTrue(TestUtils.USERNAME)).thenReturn(Optional.empty());
    when(this.passwordEncoder.encode(TestUtils.PASSWORD)).thenReturn(TestUtils.PASSWORD);
    when(this.userRepository.save(any())).thenThrow(new RuntimeException());

    assertThrows(RepositoryFailedException.class, () -> this.userService.saveUser(userRequest));
  }

  @Test
  void whenFindUserThenOk() {
    when(this.userRepository.findByEmailAndIsActiveTrue(TestUtils.USERNAME)).thenReturn(
        Optional.of(TestUtils.getUser()));

    assertEquals(TestUtils.getUser(), this.userService.findUser(TestUtils.USERNAME));
  }

  @Test
  void whenFindUserThenThrowNotFoundException() {
    when(this.userRepository.findByEmailAndIsActiveTrue(TestUtils.USERNAME)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> this.userService.findUser(TestUtils.USERNAME));
  }

  @Test
  void whenFindUserThenThrowRepositoryFailedException() {
    when(this.userRepository.findByEmailAndIsActiveTrue(TestUtils.USERNAME)).thenThrow(new RuntimeException());

    assertThrows(RepositoryFailedException.class, () -> this.userService.findUser(TestUtils.USERNAME));
  }

  @Test
  void whenFindUserResponseThenOk() {
    UserResponse response = UserResponse.fromUser(TestUtils.getUser());

    when(this.userRepository.findByEmailAndIsActiveTrue(TestUtils.USERNAME)).thenReturn(
        Optional.of(TestUtils.getUser()));

    assertEquals(response, this.userService.findUserResponse(TestUtils.USERNAME));
  }

  @Test
  void whenSaveUserLoginDataThenOk() {
    User user = TestUtils.getUserWithToken();
    when(this.userRepository.findByEmailAndIsActiveTrue(TestUtils.USERNAME)).thenReturn(Optional.of(user));
    when(this.userRepository.save(user)).thenReturn(user);

    this.userService.saveUserLoginData(TestUtils.USERNAME, TestUtils.TOKEN);

    verify(this.userRepository).save(user);
  }

  @Test
  void whenSaveUserLoginDataTokenIsNullThenOk() {
    User user = TestUtils.getUser();
    when(this.userRepository.findByEmailAndIsActiveTrue(TestUtils.USERNAME)).thenReturn(Optional.of(user));
    when(this.userRepository.save(user)).thenReturn(user);

    this.userService.saveUserLoginData(TestUtils.USERNAME, TestUtils.TOKEN);

    verify(this.userRepository).save(user);
  }

  @Test
  void whenDeleteThenOk() {
    when(this.userRepository.findByEmailAndIsActiveTrue(TestUtils.USERNAME)).thenReturn(
        Optional.of(TestUtils.getUser()));

    this.userService.deleteUser(TestUtils.USERNAME);

    verify(this.userRepository).delete(any());
  }

  @Test
  void whenDeleteThenThrowsRepositoryFailedException() {
    when(this.userRepository.findByEmailAndIsActiveTrue(TestUtils.USERNAME)).thenReturn(
        Optional.of(TestUtils.getUser()));

    doThrow(new RuntimeException()).when(this.userRepository).delete(any());

    assertThrows(RepositoryFailedException.class, () -> this.userService.deleteUser(TestUtils.USERNAME));
  }
}
