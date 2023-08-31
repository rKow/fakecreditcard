package com.coderdkk.crypto;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.Plaintext;

@ConditionalOnProperty(name = "spring.cloud.vault.enabled", havingValue = "true")
@Service
@RequiredArgsConstructor
public class VaultValueEncrypter {

  private final VaultOperations vaultOperations;

  public String encrypt(EncryptRequest encryptRequest) {
    return vaultOperations.opsForTransit()
            .encrypt(encryptRequest.getTransitKey(), Plaintext.of(encryptRequest.getDecryptedValue()))
            .getCiphertext();
  }

}
