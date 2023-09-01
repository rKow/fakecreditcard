package com.coderdkk.creditcard;

public class CreditCardNumberNotValidCharacterException extends RuntimeException {
  public CreditCardNumberNotValidCharacterException() {
    super("Provided number contains not correct character.");
  }
}
