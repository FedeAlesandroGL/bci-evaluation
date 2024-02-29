package com.bci.evaluation.service;

import com.bci.evaluation.exception.BadCredentialsException;
import com.bci.evaluation.exception.NotFoundException;
import com.bci.evaluation.service.impl.JwtUserDetailsService;
import com.bci.evaluation.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtUserDetailsServiceTest {

  UserService userService;
  JwtUserDetailsService jwtUserDetailsService;

  @BeforeEach
  void setUp() {
    this.userService = mock(UserService.class);
    this.jwtUserDetailsService = new JwtUserDetailsService(this.userService);
  }

  @Test
  void whenLoadUserByUsernameThenOk() {
    when(this.userService.findUser(TestUtils.USERNAME)).thenReturn(TestUtils.getUser());

    assertEquals(TestUtils.getSecurityUser(), this.jwtUserDetailsService.loadUserByUsername(TestUtils.USERNAME));
  }

  @Test
  void whenLoadUserByUsernameThenThrowBadCredentialsException() {
    when(this.userService.findUser(TestUtils.USERNAME)).thenThrow(new NotFoundException("User not found"));

    assertThrows(BadCredentialsException.class,
        () -> this.jwtUserDetailsService.loadUserByUsername(TestUtils.USERNAME));
  }

  @Test
  void whenFirstTimeLoadUserByUsernameThenOk() {
    assertEquals(TestUtils.getSecurityUser(),
        this.jwtUserDetailsService.firstTimeLoadUserByUsername(TestUtils.USERNAME, TestUtils.PASSWORD));
  }
}
