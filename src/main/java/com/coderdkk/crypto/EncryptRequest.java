package com.coderdkk.crypto;

import lombok.Data;

@Data
public class EncryptRequest {
  private String decryptedValue;
  private String secretKey;
  private String transitKey;
}
