package com.bci.evaluation.service.impl;

import com.bci.evaluation.dto.UserResponse;
import com.bci.evaluation.exception.BadCredentialsException;
import com.bci.evaluation.exception.NotFoundException;
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
    UserResponse user;

    try {
      user = this.userService.findUserResponse(username);
    } catch (NotFoundException e) {
      throw new BadCredentialsException("The credentials are invalid, please check them and try again");
    }

    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
        new ArrayList<>());
  }

}
