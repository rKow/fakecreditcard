package com.coderdkk.creditcard.fake.generation;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FakeCreditCardResultWriter implements ItemWriter<FakeCreditCardResult> {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public void write(Chunk<? extends FakeCreditCardResult> chunk) throws Exception {
    String sql = "update credit_cards set " +
            "human_readable_number = :human_readable_number " +
            ", encrypted_number = :encrypted_credit_card_number" +
            ", expiration_date = :expiration_date " +
            "where id = :id";

    List<MapSqlParameterSource> mapSqlParameterSources = chunk.getItems()
            .stream()
            .map(item -> {
              MapSqlParameterSource parameterSource = new MapSqlParameterSource();
              parameterSource.addValue("human_readable_number", item.getHumanReadableCreditCardNumber());
              parameterSource.addValue("encrypted_credit_card_number", item.getEncryptedCreditCardNumber());
              parameterSource.addValue("expiration_date", item.getExpirationDate());
              parameterSource.addValue("id", item.getId());
              return parameterSource;
            }).toList();

    jdbcTemplate.batchUpdate(sql, mapSqlParameterSources.toArray(new SqlParameterSource[mapSqlParameterSources.size()]));

  }
}
