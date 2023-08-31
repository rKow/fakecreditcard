package com.coderdkk.crypto;

import lombok.Data;

@Data
public class DecryptRequest {
  private String encryptedValue;
  private String secretKey;
  private String transitKey;
}
