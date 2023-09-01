package com.coderdkk.creditcard.fake.generation;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FakeCreditCardItemRowMapper implements RowMapper<FakeCreditCardItem> {
  @Override
  public FakeCreditCardItem mapRow(ResultSet rs, int rowNum) throws SQLException {
    FakeCreditCardItem processingItem = new FakeCreditCardItem();
    processingItem.setId(rs.getLong("id"));
    processingItem.setGenerationId(rs.getString("generation_id"));
    return processingItem;
  }
}
