package com.bci.evaluation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAuthentication {

  private String username;
  private String password;
}
