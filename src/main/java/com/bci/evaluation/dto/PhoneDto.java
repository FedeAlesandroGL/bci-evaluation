package com.bci.evaluation.dto;

import com.bci.evaluation.model.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {

  private String number;

  private String cityCode;

  private String countryCode;

  public static PhoneDto fromPhone(Phone phone) {
    return PhoneDto.builder()
        .number(phone.getNumber())
        .cityCode(phone.getCityCode())
        .countryCode(phone.getCountryCode())
        .build();
  }
}
