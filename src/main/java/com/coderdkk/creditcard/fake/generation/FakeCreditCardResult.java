package com.coderdkk.creditcard.fake.generation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class FakeCreditCardResult {
  private LocalDate expirationDate;
  private String creditCardNumber;
  private String humanReadableCreditCardNumber;
  private String encryptedCreditCardNumber;
  private Long id;
}
