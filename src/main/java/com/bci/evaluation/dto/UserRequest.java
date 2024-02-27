package com.bci.evaluation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserRequest {
  private String name;
  private String email;
  private String password;
  private List<PhoneRequest> phones;

}
