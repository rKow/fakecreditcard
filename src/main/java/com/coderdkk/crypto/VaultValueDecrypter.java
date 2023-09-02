package com.coderdkk.crypto;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.Ciphertext;

import java.util.List;

@ConditionalOnProperty(name = "spring.cloud.vault.enabled", havingValue = "true")
@Service
@RequiredArgsConstructor
public class VaultValueDecrypter {

  private final VaultOperations vaultOperations;

  public String decrypt(DecryptRequest decryptRequest) {
    return vaultOperations.opsForTransit()
            .decrypt(decryptRequest.getTransitKey(), Ciphertext.of(decryptRequest.getEncryptedValue()))
            .asString();
  }

  public List<String> batchDecrypt(BatchDecryptRequest decryptRequest) {
    return vaultOperations.opsForTransit()
            .decrypt(decryptRequest.getTransitKey(), decryptRequest.getEncryptedValuesAsCipherTexts())
            .stream().map(res -> res.getAsString()).toList();
  }

}
