package com.bci.evaluation.service.impl;

import com.bci.evaluation.config.security.JwtTokenUtil;
import com.bci.evaluation.dto.AuthenticationResponse;
import com.bci.evaluation.dto.UserRequest;
import com.bci.evaluation.dto.UserResponse;
import com.bci.evaluation.service.UserAuthenticationService;
import com.bci.evaluation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

  private final JwtTokenUtil jwtTokenUtil;

  private final JwtUserDetailsService jwtUserDetailsService;

  private final AuthenticationManager authenticationManager;

  private final UserService userService;

  @Override
  public AuthenticationResponse authenticate(String username, String password) {
    try {
      this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      String token = jwtTokenUtil.generateToken(this.jwtUserDetailsService.loadUserByUsername(username));
      this.userService.saveUserLoginData(username, token);

      return new AuthenticationResponse(token);
    } catch (DisabledException e) {
      throw new DisabledException("USER DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException("INVALID CREDENTIALS", e);
    }
  }

  @Override
  public UserResponse firstTimeAuthenticate(UserRequest userRequest) {
    String password = userRequest.getPassword();

    String token = jwtTokenUtil.generateToken(
        this.jwtUserDetailsService.firstTimeLoadUserByUsername(userRequest.getEmail(), userRequest.getPassword()));

    UserResponse userResponse = this.userService.saveUser(userRequest, token);

    this.authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(userRequest.getEmail(), password));

    return userResponse;
  }
}
