package com.bci.evaluation.service.impl;

import com.bci.evaluation.dto.UserRequest;
import com.bci.evaluation.dto.UserResponse;
import com.bci.evaluation.exception.AlreadyExistsException;
import com.bci.evaluation.exception.BadRequestException;
import com.bci.evaluation.exception.NotFoundException;
import com.bci.evaluation.exception.RepositoryFailedException;
import com.bci.evaluation.model.User;
import com.bci.evaluation.repository.UserRepository;
import com.bci.evaluation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Value("${email.regexp}")
  private String emailRegex;
  @Value("${password.regexp}")
  private String passwordRegex;

  private static final String ALREADY_EXISTS = "Email is already registered";
  private static final String NOT_FOUND = "User not found";
  private static final String REPOSITORY_FAILED = "Repository failed, please try again later";
  private static final String EMAIL_OR_PASSWORD_BAD_REQUEST = "Invalid email or password, check the requirements for them and try again";

  @Override
  public UserResponse saveUser(UserRequest userRequest) {

    if (this.findByEmail(userRequest.getEmail()).isPresent()) {
      throw new AlreadyExistsException(ALREADY_EXISTS);
    }

    if (!userRequest.getEmail().matches(emailRegex)
        || !userRequest.getPassword().matches(passwordRegex)) {
      throw new BadRequestException(EMAIL_OR_PASSWORD_BAD_REQUEST);
    }

    return UserResponse.fromUser(this.save(User.fromRequest(userRequest)));
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
    user.setToken(token);
    user.setLastLogin(LocalDateTime.now());

    this.save(user);
  }

  @Override
  public void deleteUser(String email) {
    User user = this.findUser(email);
    this.delete(user);
  }

  private User save(User user) {
    try {
      return userRepository.save(user);
    } catch (Exception e) {
      throw new RepositoryFailedException(REPOSITORY_FAILED);
    }
  }

  private Optional<User> findByEmail(String email) {
    try {
      return userRepository.findByEmail(email);
    } catch (Exception e) {
      throw new RepositoryFailedException(REPOSITORY_FAILED);
    }
  }

  private void delete(User user) {
    try {
      userRepository.delete(user);
    } catch (Exception e) {
      throw new RepositoryFailedException(REPOSITORY_FAILED);
    }
  }
}
