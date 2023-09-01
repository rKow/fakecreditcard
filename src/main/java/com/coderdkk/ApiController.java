package com.coderdkk;

import com.coderdkk.api.FinanceApi;
import com.coderdkk.creditcard.fake.generation.FakeEmptyCreditCardCreator;
import com.coderdkk.creditcard.fake.report.FakeCreditCardGenerationReportCreator;
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

  private final FakeEmptyCreditCardCreator fakeEmptyCreditCardCreator;
  private final FakeCreditCardGenerationStatusGetter fakeCreditCardGenerationStatusGetter;
  private final FakeCreditCardGenerationReportCreator fakeCreditCardGenerationReportCreator;

  @Override
  public ResponseEntity<String> fakeCreditCardsGeneration(FakeCreditCardGenerateRequest fakeCreditCardGenerateRequest) {
    return new ResponseEntity<>(fakeEmptyCreditCardCreator.createEmptyCreditCard(fakeCreditCardGenerateRequest), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<String> fakeCreditCardsGenerationStatus(FakeCreditCardGenerationStatusRequest fakeCreditCardGenerationStatusRequest) {
    return new ResponseEntity<>(fakeCreditCardGenerationStatusGetter.getStatus(fakeCreditCardGenerationStatusRequest), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Resource> fakeCreditCardsGenerationReport(String generationId, Boolean decrypted) {
    return new ResponseEntity<>(fakeCreditCardGenerationReportCreator.get(generationId, decrypted), HttpStatus.OK);
  }


}
