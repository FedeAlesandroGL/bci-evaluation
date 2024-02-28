package com.bci.evaluation.model;

import com.bci.evaluation.dto.UserRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET is_active = false WHERE id = ?")
public class User {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private UUID uuid;

  private String name;

  @Column(unique = true)
  private String email;

  private String password;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Phone> phones;

  @Column(name = "last_login")
  private LocalDateTime lastLogin;

  private String token;

  @Column(name = "is_active")
  private Boolean isActive;

  private LocalDateTime created;

  @UpdateTimestamp
  private LocalDateTime modified;

  public static User fromRequest(UserRequest userRequest) {
    LocalDateTime now = LocalDateTime.now();
    return User.builder()
        .isActive(true)
        .created(now)
        .lastLogin(now)
        .uuid(UUID.randomUUID())
        .name(userRequest.getName())
        .email(userRequest.getEmail())
        .password(userRequest.getPassword())
        .phones(userRequest.getPhones() == null ?
            new ArrayList<>() :
            userRequest.getPhones().stream().map(Phone::fromDto).toList())
        .build();
  }

}
