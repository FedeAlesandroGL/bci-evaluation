package com.bci.evaluation.service;

import com.bci.evaluation.config.security.JwtTokenUtil;
import com.bci.evaluation.dto.AuthenticationResponse;
import com.bci.evaluation.service.impl.JwtUserDetailsService;
import com.bci.evaluation.service.impl.UserAuthenticationServiceImpl;
import com.bci.evaluation.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAuthenticationServiceTest {

  JwtTokenUtil jwtTokenUtil;
  JwtUserDetailsService jwtUserDetailsService;
  AuthenticationManager authenticationManager;
  UserService userService;
  UserAuthenticationService userAuthenticationService;

  @BeforeEach
  void setUp() {
    this.jwtTokenUtil = mock(JwtTokenUtil.class);
    this.jwtUserDetailsService = mock(JwtUserDetailsService.class);
    this.authenticationManager = mock(AuthenticationManager.class);
    this.userService = mock(UserService.class);
    this.userAuthenticationService = new UserAuthenticationServiceImpl(this.jwtTokenUtil, this.jwtUserDetailsService,
        this.authenticationManager, this.userService);
  }

  @Test
  void whenAuthenticateThenOk() {
    when(this.jwtTokenUtil.generateToken(any())).thenReturn(TestUtils.TOKEN);

    assertEquals(new AuthenticationResponse(TestUtils.TOKEN),
        this.userAuthenticationService.authenticate(TestUtils.USERNAME, TestUtils.PASSWORD));
  }

  @Test
  void whenAuthenticateThenThrowDisabledException() {
    doThrow(new DisabledException(TestUtils.MESSAGE)).when(this.authenticationManager).authenticate(any());

    assertThrows(DisabledException.class,
        () -> this.userAuthenticationService.authenticate(TestUtils.USERNAME, TestUtils.PASSWORD));
  }

  @Test
  void whenAuthenticateThenThrowBadCredentialsException() {
    doThrow(new BadCredentialsException(TestUtils.MESSAGE)).when(this.authenticationManager).authenticate(any());

    assertThrows(BadCredentialsException.class,
        () -> this.userAuthenticationService.authenticate(TestUtils.USERNAME, TestUtils.PASSWORD));
  }

}
