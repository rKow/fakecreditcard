package com.coderdkk.creditcard.fake.generation;

import com.coderdkk.crypto.EncryptRequest;
import com.coderdkk.crypto.VaultValueEncrypter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class FakeCreditCardNumberGenerator {

  private static final Pattern ONLY_NUMBER_PATTERN = Pattern.compile("\\d");

  @Value("${crypto.transit-key:default}")
  private String transitKey;

  private final VaultValueEncrypter vaultValueEncrypter;

  public FakeCreditCardResult getNewFake(CreditCardItem item) {
    CrediCardNumberLuhnAlgorithmValidatableGenerator crediCardNumberLuhnAlgorithmValidatableGenerator = new CrediCardNumberLuhnAlgorithmValidatableGenerator();
    String creditCardNumber = crediCardNumberLuhnAlgorithmValidatableGenerator.getNumber(CreditCardType.MASTER_CARD);
    return new FakeCreditCardResult(
            LocalDate.now().plusYears(2)
            , creditCardNumber
            , onlyLastFourChars(creditCardNumber)
            , encrypt(creditCardNumber)
            , item.getId());
  }

  public String encrypt(String creditCardNumber) {
    EncryptRequest request = new EncryptRequest();
    request.setDecryptedValue(creditCardNumber);
    request.setTransitKey(transitKey);
    return vaultValueEncrypter.encrypt(request);
  }

  public String onlyLastFourChars(String creditCardNumber) {
    String lastFourNumbers = creditCardNumber
            .substring(creditCardNumber.length() - 4);
    String prefix = ONLY_NUMBER_PATTERN.matcher(creditCardNumber.substring(0, creditCardNumber.length() - 4)).replaceAll("*");
    return prefix + lastFourNumbers;
  }
}
