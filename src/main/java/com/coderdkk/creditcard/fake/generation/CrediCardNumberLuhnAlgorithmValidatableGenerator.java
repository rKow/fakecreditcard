package com.coderdkk.creditcard.fake.generation;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class CrediCardNumberLuhnAlgorithmValidatableGenerator {

  public String getNumber(CreditCardType creditCardType) {
    String prefixOfCreditCardNumber = creditCardType.getPrefixes().stream().findAny().orElseThrow();
    int lengthOfCreditCardNumber = creditCardType.getLength();
    int lengthOfNumberToGenerate = lengthOfCreditCardNumber - prefixOfCreditCardNumber.length();

    String creditCardRandomNumber = prefixOfCreditCardNumber + calculateRandomNumber(lengthOfNumberToGenerate);
    int luhnResult = getLuhnResult(creditCardRandomNumber);
    int newLastDigit = changeLastDigitToFitLuhnAlgorithmValidator(creditCardRandomNumber, luhnResult);
    return replaceLastDigitWithNewOne(creditCardRandomNumber, newLastDigit);
  }

  private String replaceLastDigitWithNewOne(String creditCardRandomNumber, int newLastDigit) {
    return creditCardRandomNumber.substring(0, creditCardRandomNumber.length() - 1) + newLastDigit;
  }

  private int getLuhnResult(String randomNumber) {
    List<Character> eachSecondDigit = getEachSecondDigit(randomNumber, 1);
    List<Character> otherDigits = getEachSecondDigit(randomNumber, 0);
    Integer doubleEachDigitAndSum = doubleEachDigitAndSum(eachSecondDigit);
    Integer sumOfOtherDigits = sumDigits(otherDigits);
    return (doubleEachDigitAndSum + sumOfOtherDigits) % 10;
  }

  private int changeLastDigitToFitLuhnAlgorithmValidator(String randomNumber, int luhnResult) {
    int lastDigit = Integer.parseInt(String.valueOf(randomNumber.charAt(randomNumber.length() - 1)));
    if (luhnResult != 0) {
      if (lastDigit < luhnResult) {
        return lastDigit + (10 - luhnResult);
      } else if (lastDigit == luhnResult) {
        return 0;
      } else {
        return lastDigit - luhnResult;
      }
    }

    return lastDigit;
  }

  private Integer doubleEachDigitAndSum(List<Character> digits) {
    List<Character> newDigits = digits.stream()
            .map(chr -> Integer.parseInt(String.valueOf(chr.charValue())))
            .map(i -> i * 2)
            .flatMap(i -> i.toString().chars()
                    .mapToObj(c -> (char) c).toList()
                    .stream())
            .toList();

    return sumDigits(newDigits);

  }

  private Integer sumDigits(List<Character> digits) {
    return digits.stream()
            .map(chr -> Integer.parseInt(String.valueOf(chr)))
            .reduce(0, Integer::sum);

  }

  private List<Character> getEachSecondDigit(String randomNumber, int startFrom) {

    List<Character> characters = reverseWord(randomNumber).chars()
            .mapToObj(c -> (char) c)
            .toList();

    return IntStream.range(0, characters.size())
            .filter(n -> n % 2 == startFrom)
            .mapToObj(characters::get)
            .toList();
  }


  private String calculateRandomNumber(int lengthOfNumberToGenerate) {
    ThreadLocalRandom random = ThreadLocalRandom.current();
    long anyLongWithLengthGreaterThanOrEqual16 = random.nextLong(10_000_000_000_000_000L, 100_000_000_000_000_000L);
    String s = Long.toString(anyLongWithLengthGreaterThanOrEqual16);
    return s.substring(0, lengthOfNumberToGenerate - 2);
  }

  private String reverseWord(String str) {
    return new StringBuilder(str).reverse().toString();
  }
}
