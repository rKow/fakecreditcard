package com.coderdkk.creditcard;

import lombok.Getter;

import java.util.List;

@Getter
public enum CreditCardType {

  AMERICAN_EXPRESS(List.of("34", "37"), 15)
  , MASTER_CARD(List.of("51", "52", "53", "54", "55"), 16)
  , VISA(List.of("4"), 16)
  , DINERS_CLUB_AND_CARTE_BLANCHE(List.of("36", "38", "300", "301", "302", "303", "304", "305"), 14)
  , DISCOVER(List.of("6011"), 16)
  , JCB15(List.of("2123", "1800"), 15)
  , JCB16(List.of("3"), 16);

  private final List<String> prefixes;
  private final Integer length;

  CreditCardType(List<String> prefixes, Integer length) {
    this.prefixes = prefixes;
    this.length = length;
  }

}
