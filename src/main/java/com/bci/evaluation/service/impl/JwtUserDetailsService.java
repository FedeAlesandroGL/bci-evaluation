package com.bci.evaluation.service.impl;

import com.bci.evaluation.exception.BadCredentialsException;
import com.bci.evaluation.exception.NotFoundException;
import com.bci.evaluation.model.User;
import com.bci.evaluation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user;
    try {
      user = this.userService.findUser(username);
    } catch (NotFoundException e) {
      throw new BadCredentialsException("INVALID CREDENTIALS");
    }

    if (user.getEmail().equals(username)) {
      return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
          new ArrayList<>());
    } else {
      throw new NotFoundException("User not found with username: " + username);
    }
  }

  public UserDetails firstTimeLoadUserByUsername(String username, String password) {

    return new org.springframework.security.core.userdetails.User(username, password, new ArrayList<>());
  }

}
