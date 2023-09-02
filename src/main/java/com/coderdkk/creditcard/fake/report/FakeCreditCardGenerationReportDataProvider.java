package com.coderdkk.creditcard.fake.report;

import com.coderdkk.crypto.DecryptRequest;
import com.coderdkk.crypto.VaultValueDecrypter;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FakeCreditCardGenerationReportDataProvider {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final VaultValueDecrypter decrypter;

  @Value("${crypto.transit-key:default}")
  private String transitKey;

  public VelocityContext getContext(String generationId, boolean decrypted) {

    String sql = "select expiration_date, human_readable_number masked_credit_card_number, encrypted_number credit_card_number " +
            "from credit_cards where generation_id = :generation_id ";

    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
    parameterSource.addValue("generation_id", generationId);
    List<CreditCardReportItem> result = jdbcTemplate.query(sql, parameterSource, new CreditCardReportItemRowMapper());

    if (decrypted) {
      result.forEach(creditCardReportItem -> creditCardReportItem.setCreditCardNumber(getDecryptedValue(creditCardReportItem)));
    }

    return getVelocityContext(result);
  }

  private VelocityContext getVelocityContext(List<CreditCardReportItem> result) {
    VelocityContext context = new VelocityContext();
    context.put("creditCards", result);

    return context;
  }

  private String getDecryptedValue(CreditCardReportItem creditCardReportItem) {
    DecryptRequest decryptRequest = new DecryptRequest();
    decryptRequest.setEncryptedValue(creditCardReportItem.getCreditCardNumber());
    decryptRequest.setTransitKey(transitKey);
    return decrypter.decrypt(decryptRequest);
  }
}
