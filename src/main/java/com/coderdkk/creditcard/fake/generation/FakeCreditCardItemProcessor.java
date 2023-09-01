package com.coderdkk.creditcard.fake.generation;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FakeCreditCardItemProcessor implements ItemProcessor<FakeCreditCardItem, FakeCreditCardResult> {

  private final FakeCreditCardGenerator fakeCreditCardGenerator;

  @Override
  public FakeCreditCardResult process(FakeCreditCardItem item) throws Exception {
    return fakeCreditCardGenerator.getNewFake(item);
  }
}
