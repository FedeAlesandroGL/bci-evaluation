package com.bci.evaluation.service.impl;

import com.bci.evaluation.dto.UserRequest;
import com.bci.evaluation.dto.UserResponse;
import com.bci.evaluation.exception.AlreadyExistsException;
import com.bci.evaluation.exception.BadRequestException;
import com.bci.evaluation.exception.NotFoundException;
import com.bci.evaluation.exception.RepositoryFailedException;
import com.bci.evaluation.model.User;
import com.bci.evaluation.repository.UserRepository;
import com.bci.evaluation.service.PhoneService;
import com.bci.evaluation.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final PhoneService phoneService;

  @Value("${email.regexp}")
  private String emailRegex;
  @Value("${password.regexp}")
  private String passwordRegex;

  private static final String ALREADY_EXISTS = "Email is already registered";
  private static final String NOT_FOUND = "User not found";
  private static final String REPOSITORY_FAILED = "Repository failed, please try again later";
  private static final String REPOSITORY_FAILED_LOG = "Repository failed, error -> {}";
  private static final String EMAIL_OR_PASSWORD_BAD_REQUEST = "Invalid email or password, check the requirements for them and try again";

  @Override
  public UserResponse saveUser(UserRequest userRequest, String token) {

    if (this.findByEmail(userRequest.getEmail()).isPresent()) {
      throw new AlreadyExistsException(ALREADY_EXISTS);
    }

    if (!userRequest.getEmail().matches(emailRegex)
        || !userRequest.getPassword().matches(passwordRegex)) {
      throw new BadRequestException(EMAIL_OR_PASSWORD_BAD_REQUEST);
    }

    userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

    User user = this.save(User.fromRequest(userRequest, token));
    user.getPhones().forEach(phone -> phone.setUser(user));

    phoneService.saveAll(user.getPhones());

    return UserResponse.fromUser(user);
  }

  @Override
  public User findUser(String email) {
    return this.findByEmail(email).orElseThrow(() -> new NotFoundException(NOT_FOUND));
  }

  @Override
  public UserResponse findUserResponse(String email) {
    return UserResponse.fromUser(this.findUser(email));
  }

  @Override
  public void saveUserLoginData(String email, String token) {
    User user = this.findUser(email);

    if (user.getToken() != null) {
      user.setLastLogin(LocalDateTime.now());
    }

    user.setToken(token);

    this.save(user);
  }

  @Override
  public void deleteUser(String email) {
    User user = this.findUser(email);
    this.delete(user);
  }

  private User save(User user) {
    Optional<User> userOptional = this.findByEmailIsNotActive(user.getEmail());

    userOptional.ifPresent(value -> user.setId(value.getId()));

    try {
      return userRepository.save(user);
    } catch (Exception e) {
      log.error(REPOSITORY_FAILED_LOG, e.getMessage());
      throw new RepositoryFailedException(REPOSITORY_FAILED);
    }
  }

  private Optional<User> findByEmail(String email) {
    try {
      return userRepository.findByEmailAndIsActiveTrue(email);
    } catch (Exception e) {
      log.error(REPOSITORY_FAILED_LOG, e.getMessage());
      throw new RepositoryFailedException(REPOSITORY_FAILED);
    }
  }

  private Optional<User> findByEmailIsNotActive(String email) {
    try {
      return userRepository.findByEmailAndIsActiveFalse(email);
    } catch (Exception e) {
      log.error(REPOSITORY_FAILED_LOG, e.getMessage());
      throw new RepositoryFailedException(REPOSITORY_FAILED);
    }
  }

  private void delete(User user) {
    try {
      userRepository.delete(user);
    } catch (Exception e) {
      log.error(REPOSITORY_FAILED_LOG, e.getMessage());
      throw new RepositoryFailedException(REPOSITORY_FAILED);
    }
  }
}
