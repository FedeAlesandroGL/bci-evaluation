package com.bci.evaluation.model;

import com.bci.evaluation.dto.PhoneRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "phone")
public class Phone {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String number;

  @Column(name = "city_code")
  private String cityCode;

  @Column(name = "country_code")
  private String countryCode;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public static Phone fromDto(PhoneRequest phoneDto) {
    return Phone.builder()
        .number(phoneDto.getNumber())
        .cityCode(phoneDto.getCityCode())
        .countryCode(phoneDto.getCountryCode())
        .build();
  }
}
