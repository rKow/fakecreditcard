package com.coderdkk.creditcard.fake.status;

import com.coderdkk.datamodel.FakeCreditCardGenerationStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FakeCreditCardGenerationStatusGetter {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public String getStatus(FakeCreditCardGenerationStatusRequest fakeCreditCardGenerationStatusRequest) {
    return "75";
  }

}
