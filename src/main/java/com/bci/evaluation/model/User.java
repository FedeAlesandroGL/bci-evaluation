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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Where(clause = "is_active = true")
@SQLDelete(sql = "UPDATE user SET is_active = false WHERE email = ?")
public class User {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private UUID uuid;

  private String name;

  private String email;

  private String password;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Phone> phones;

  @CreationTimestamp
  @Column(name = "last_login")
  private LocalDateTime lastLogin;

  private String token;

  @Column(name = "is_active", columnDefinition = "boolean default true")
  private Boolean isActive;

  @CreationTimestamp
  private LocalDateTime created;

  @UpdateTimestamp
  private LocalDateTime modified;

  public static User fromRequest(UserRequest userRequest) {
    return User.builder()
        .name(userRequest.getName())
        .email(userRequest.getEmail())
        .password(userRequest.getPassword())
        .phones(userRequest.getPhones().stream().map(Phone::fromDto).toList())
        .build();
  }

}
