package com.bci.evaluation.service;

import com.bci.evaluation.dto.AuthenticationResponse;
import com.bci.evaluation.dto.UserRequest;
import com.bci.evaluation.dto.UserResponse;

public interface UserAuthenticationService {

  AuthenticationResponse authenticate(String username, String password);

  UserResponse firstTimeAuthenticate(UserRequest userRequest);
}
