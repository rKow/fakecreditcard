package com.coderdkk.creditcard;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class LuhnCalculator {

  private static final Pattern POSSIBLE_CREDIT_CARD_NUMBER_CHARS = Pattern.compile("[- ]");

  public int calculate(String randomNumber) {
    randomNumber = POSSIBLE_CREDIT_CARD_NUMBER_CHARS.matcher(randomNumber).replaceAll("");
    isRandomNumber(randomNumber);
    List<Character> eachSecondDigit = getEachSecondDigit(randomNumber, 1);
    List<Character> otherDigits = getEachSecondDigit(randomNumber, 0);
    Integer doubleEachDigitAndSum = doubleEachDigitAndSum(eachSecondDigit);
    Integer sumOfOtherDigits = sumDigits(otherDigits);
    return (doubleEachDigitAndSum + sumOfOtherDigits) % 10;
  }

  private void isRandomNumber(String randomNumber) {
    boolean isNumber = Pattern.compile("^\\d*$").matcher(randomNumber).matches();
    if (!isNumber) {
      throw new CreditCardNumberNotValidCharacterException();
    }
  }

  private Integer doubleEachDigitAndSum(List<Character> digits) {
    List<Character> newDigits = digits.stream()
            .map(chr -> Integer.parseInt(String.valueOf(chr.charValue())) * 2)
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

  private String reverseWord(String str) {
    return new StringBuilder(str).reverse().toString();
  }

}
