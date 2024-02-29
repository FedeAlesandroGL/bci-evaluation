package com.bci.evaluation.dto;

import com.bci.evaluation.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

  private String id;
  private String name;
  private String email;
  private String password;
  private List<PhoneDto> phones;
  private String token;
  private Boolean isActive;
  private LocalDateTime created;
  private LocalDateTime modified;
  private LocalDateTime lastLogin;

  public static UserResponse fromUser(User user) {
    return UserResponse.builder()
        .id(user.getUuid().toString())
        .name(user.getName())
        .email(user.getEmail())
        .password(user.getPassword())
        .phones(user.getPhones().stream().map(PhoneDto::fromPhone).toList())
        .token(user.getToken())
        .isActive(user.getIsActive())
        .created(user.getCreated())
        .modified(user.getModified())
        .lastLogin(user.getLastLogin())
        .build();
  }
}
