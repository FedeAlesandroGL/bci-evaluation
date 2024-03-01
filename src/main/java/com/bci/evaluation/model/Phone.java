package com.bci.evaluation.model;

import com.bci.evaluation.dto.PhoneDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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

  public static Phone fromDto(PhoneDto phoneDto) {
    return Phone.builder()
        .number(phoneDto.getNumber())
        .cityCode(phoneDto.getCityCode())
        .countryCode(phoneDto.getCountryCode())
        .build();
  }
}
