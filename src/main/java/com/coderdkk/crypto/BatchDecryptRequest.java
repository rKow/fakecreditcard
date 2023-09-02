package com.coderdkk.crypto;

import lombok.Data;
import org.springframework.vault.support.Ciphertext;

import java.util.List;

@Data
public class BatchDecryptRequest {
  private List<String> encryptedValues;
  private String secretKey;
  private String transitKey;

  public List<Ciphertext> getEncryptedValuesAsCipherTexts() {
    return encryptedValues.stream().map(Ciphertext::of).toList();
  }
}
