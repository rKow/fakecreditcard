package com.coderdkk.creditcard.fake.report;

import com.coderdkk.creditcard.fake.generation.FakeCreditCardItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;

public class CreditCardReportItemRowMapper implements RowMapper<CreditCardReportItem> {
  @Override
  public CreditCardReportItem mapRow(ResultSet rs, int rowNum) throws SQLException {
    CreditCardReportItem processingItem = new CreditCardReportItem();
    processingItem.setCreditCardNumber(rs.getString("credit_card_number"));
    processingItem.setMaskedCreditCardNumber(rs.getString("masked_credit_card_number"));
    processingItem.setExpirationDate(rs.getDate("expiration_date").toLocalDate());
    return processingItem;
  }
}
