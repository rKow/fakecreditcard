package com.coderdkk.creditcard.fake.report;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreditCardReportItem {
  String maskedCreditCardNumber;
  String creditCardNumber;
  LocalDate expirationDate;

}
