package com.coderdkk.creditcard.fake.generation;

import com.coderdkk.creditcard.CreditCardType;
import com.coderdkk.creditcard.LuhnCalculator;

import java.util.concurrent.ThreadLocalRandom;

public class FakeCreditCardNumberGenerator {

  public String getNumber(CreditCardType creditCardType) {
    String prefixOfCreditCardNumber = creditCardType.getPrefixes().stream().findAny().orElseThrow();
    int lengthOfCreditCardNumber = creditCardType.getLength();
    int lengthOfNumberToGenerate = lengthOfCreditCardNumber - prefixOfCreditCardNumber.length();

    String creditCardRandomNumber = prefixOfCreditCardNumber + calculateRandomNumber(lengthOfNumberToGenerate);
    LuhnCalculator luhnCalculator = new LuhnCalculator();
    int luhnResult = luhnCalculator.calculate(creditCardRandomNumber);
    int newLastDigit = changeLastDigitToFitLuhnAlgorithmValidator(creditCardRandomNumber, luhnResult);
    return replaceLastDigitWithNewOne(creditCardRandomNumber, newLastDigit);
  }

  private String replaceLastDigitWithNewOne(String creditCardRandomNumber, int newLastDigit) {
    return creditCardRandomNumber.substring(0, creditCardRandomNumber.length() - 1) + newLastDigit;
  }


  private int changeLastDigitToFitLuhnAlgorithmValidator(String randomNumber, int luhnResult) {
    int lastDigit = Integer.parseInt(String.valueOf(randomNumber.charAt(randomNumber.length() - 1)));
    if (luhnResult == 0) {
      return lastDigit;
    }
    if (lastDigit < luhnResult) {
      return lastDigit + (10 - luhnResult);
    } else if (lastDigit == luhnResult) {
      return 0;
    } else {
      return lastDigit - luhnResult;
    }
  }

  private String calculateRandomNumber(int lengthOfNumberToGenerate) {
    ThreadLocalRandom random = ThreadLocalRandom.current();
    long anyLongWithLengthGreaterThanOrEqual16 = random.nextLong(10_000_000_000_000_000L, 100_000_000_000_000_000L);
    String s = Long.toString(anyLongWithLengthGreaterThanOrEqual16);
    return s.substring(0, lengthOfNumberToGenerate);
  }

}
