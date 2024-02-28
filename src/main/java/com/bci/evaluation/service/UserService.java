package com.bci.evaluation.service;

import com.bci.evaluation.dto.UserRequest;
import com.bci.evaluation.dto.UserResponse;
import com.bci.evaluation.model.User;

public interface UserService {

  User findUser(String email);

  UserResponse findUserResponse(String email);

  UserResponse saveUser(UserRequest user, String token);

  void saveUserLoginData(String email, String token);

  void deleteUser(String email);
}
