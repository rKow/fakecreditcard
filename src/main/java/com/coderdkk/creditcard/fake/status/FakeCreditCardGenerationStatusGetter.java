package com.coderdkk.creditcard.fake.status;

import com.coderdkk.datamodel.FakeCreditCardGenerationStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class FakeCreditCardGenerationStatusGetter {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public String getStatus(FakeCreditCardGenerationStatusRequest fakeCreditCardGenerationStatusRequest) {
    String sql = "select AVG(CASE WHEN t.encrypted_number is not null THEN 1.0 ELSE 0 END) * 100\n" +
            "from credit_cards t where generation_id = :generation_id";
    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
    parameterSource.addValue("generation_id", fakeCreditCardGenerationStatusRequest.getGenerationId());
    try {
      BigDecimal result = jdbcTemplate.queryForObject(sql, parameterSource, new SingleColumnRowMapper<>(BigDecimal.class));
      return String.format("Progress status: %s%%", result.setScale(2));
    } catch (EmptyResultDataAccessException e) {
      throw new NoSuchGenerationException();
    }

  }

}
