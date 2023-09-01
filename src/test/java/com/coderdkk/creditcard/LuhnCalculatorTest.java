package com.coderdkk.creditcard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LuhnCalculatorTest {

  @Test
  void whenNotNumberProvided_shouldReturnException() {
    LuhnCalculator calculator = new LuhnCalculator();
    assertThrows(CreditCardNumberNotValidCharacterException.class, () -> calculator.calculate("a46247482Z33249780"));
  }

  @Test
  void whenValidCreditCardNumberProvided_shouldReturnZero() {
    LuhnCalculator calculator = new LuhnCalculator();
    int result = calculator.calculate("4624 7482 3324 9780");
    assertEquals(0, result);
  }

  @Test
  void whenNotValidCreditCardNumberProvided_shouldReturnOtherThanZero() {
    LuhnCalculator calculator = new LuhnCalculator();
    int result = calculator.calculate("4624748233249080");
    assertEquals(3, result);
  }
}
