package com.bci.evaluation.dto;

import com.bci.evaluation.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

  private String uuid;
  private LocalDateTime created;
  private LocalDateTime modified;
  private LocalDateTime lastLogin;
  private String token;
  private Boolean isActive;

  public static UserResponse fromUser(User user) {
    return UserResponse.builder()
        .uuid(user.getUuid().toString())
        .created(user.getCreated())
        .modified(user.getModified())
        .lastLogin(user.getLastLogin())
        .token(user.getToken())
        .isActive(user.getIsActive())
        .build();
  }
}
