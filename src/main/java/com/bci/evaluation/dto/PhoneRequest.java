package com.bci.evaluation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhoneRequest {

  private String number;

  private String cityCode;

  private String countryCode;
}
