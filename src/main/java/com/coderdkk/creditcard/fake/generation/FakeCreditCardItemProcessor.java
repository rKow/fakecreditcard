package com.coderdkk.creditcard.fake.generation;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FakeCreditCardItemProcessor implements ItemProcessor<CreditCardItem, FakeCreditCardResult> {

  private final FakeCreditCardNumberGenerator fakeCreditCardNumberGenerator;

  @Override
  public FakeCreditCardResult process(CreditCardItem item) throws Exception {
    return fakeCreditCardNumberGenerator.getNewFake(item);
  }
}
