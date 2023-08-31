package com.coderdkk.creditcard.fake.generation;

import com.coderdkk.AsyncProcessManager;
import com.coderdkk.datamodel.FakeCreditCardGenerateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@Slf4j
@RequiredArgsConstructor
public class FakeCreditCardGenerationCreator {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final AsyncProcessManager asyncProcessManager;

  public String createEmptyCreditCard(FakeCreditCardGenerateRequest fakeCreditCardGenerateRequest) {
    String newTaskId = asyncProcessManager.getNewTaskId();
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future<?> task = executorService.submit(() -> {
      try {
        String sql = "WITH recursive t(n) AS ( " +
                "    VALUES (1) " +
                "  UNION ALL " +
                "    SELECT n+1 FROM t WHERE n < :number_of_items " +
                ")" +
                " insert into credit_cards (generation_id) " +
                "select :generation_id from t";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("number_of_items", fakeCreditCardGenerateRequest.getNumberOfCreditCardsToGenerate());
        parameterSource.addValue("generation_id", newTaskId);

        jdbcTemplate.update(sql, parameterSource);
      } catch (Exception e) {
        log.error("Could not process credit card items generation.", e);
      } finally {
        asyncProcessManager.getRunningTasks().remove(newTaskId);
      }

    });
    asyncProcessManager.registerNewTask(newTaskId, task);

    return newTaskId;

  }
}
