package com.coderdkk.creditcard.fake.generation;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditCardItemRowMapper implements RowMapper<CreditCardItem> {
  @Override
  public CreditCardItem mapRow(ResultSet rs, int rowNum) throws SQLException {
    CreditCardItem processingItem = new CreditCardItem();
    processingItem.setId(rs.getLong("id"));
    processingItem.setGenerationId(rs.getString("generation_id"));
    return processingItem;
  }
}
