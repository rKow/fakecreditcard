package com.coderdkk.creditcard.fake.status;

public class NoSuchGenerationException extends RuntimeException {
  public NoSuchGenerationException() {
    super("Generation id could not be found.");
  }
}
