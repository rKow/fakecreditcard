package com.coderdkk.creditcard;

import com.coderdkk.api.FinanceApi;
import com.coderdkk.creditcard.fake.generation.FakeCreditCardGenerationCreator;
import com.coderdkk.creditcard.fake.status.FakeCreditCardGenerationStatusGetter;
import com.coderdkk.datamodel.FakeCreditCardGenerateRequest;
import com.coderdkk.datamodel.FakeCreditCardGenerationStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@Primary
@RequiredArgsConstructor
public class ApiController implements FinanceApi {

  private final FakeCreditCardGenerationCreator fakeCreditCardGenerationCreator;
  private final FakeCreditCardGenerationStatusGetter fakeCreditCardGenerationStatusGetter;

  @Override
  public ResponseEntity<String> fakeCreditCardsGeneration(FakeCreditCardGenerateRequest fakeCreditCardGenerateRequest) {
    return new ResponseEntity<>(fakeCreditCardGenerationCreator.createEmptyCreditCard(fakeCreditCardGenerateRequest), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<String> fakeCreditCardsGenerationStatus(FakeCreditCardGenerationStatusRequest fakeCreditCardGenerationStatusRequest) {
    return new ResponseEntity<>(fakeCreditCardGenerationStatusGetter.getStatus(fakeCreditCardGenerationStatusRequest), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Resource> fakeCreditCardsGenerationReport(String generationId, Boolean decrypted) {
    return FinanceApi.super.fakeCreditCardsGenerationReport(generationId, decrypted);
  }


}
