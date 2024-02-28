package com.bci.evaluation.service;

import com.bci.evaluation.dto.AuthenticationResponse;

public interface UserAuthenticationService {

  AuthenticationResponse authenticate(String username, String password);
}
