package com.coderdkk.creditcard.fake.report;

import lombok.RequiredArgsConstructor;
import org.apache.velocity.VelocityContext;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FakeCreditCardGenerationReportDataProvider {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public VelocityContext getContext(String generationId, Boolean decrypted) {

    String sql = "select expiration_date, human_readable_number masked_credit_card_number, encrypted_number credit_card_number " +
            "from credit_cards where generation_id = :generation_id ";

    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
    parameterSource.addValue("generation_id", generationId);

    List<CreditCardReportItem> result = jdbcTemplate.query(sql, parameterSource, new CreditCardReportItemRowMapper());

    VelocityContext context = new VelocityContext();
    context.put("creditCards", result);

    return context;
  }
}
