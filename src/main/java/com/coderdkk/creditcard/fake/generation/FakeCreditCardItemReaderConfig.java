package com.coderdkk.creditcard.fake.generation;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FakeCreditCardItemReaderConfig {

  @Value("${finance.credit-card.fake.job.chunk-size:20}")
  private Integer chunkSize;

  @Bean
  @StepScope
  public JdbcPagingItemReader<FakeCreditCardItem> fakeCreditCardItemReader(
          DataSource dataSource
          , PagingQueryProvider pagingQueryProvider
  ) {

    return new JdbcPagingItemReaderBuilder<FakeCreditCardItem>()
            .name("fakeCreditCardItemReader")
            .dataSource(dataSource)
            .saveState(false)
            .pageSize(chunkSize)
            .queryProvider(pagingQueryProvider)
            .rowMapper(getRowMapper())
            .build();
  }

  private RowMapper<FakeCreditCardItem> getRowMapper() {
    return (resultSet, i) -> {
      FakeCreditCardItemRowMapper mapper = new FakeCreditCardItemRowMapper();
      return mapper.mapRow(resultSet, i);
    };
  }

  @Bean
  public PagingQueryProvider pagingQueryProvider(DataSource dataSource) throws Exception {
    SqlPagingQueryProviderFactoryBean sqlPagingQueryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
    Map<String, Order> sortKey = new HashMap<>();
    sortKey.put("id", Order.ASCENDING);
    sqlPagingQueryProviderFactoryBean.setDataSource(dataSource);
    sqlPagingQueryProviderFactoryBean.setSelectClause("select generation_id, id");
    sqlPagingQueryProviderFactoryBean.setFromClause("from credit_cards");
    sqlPagingQueryProviderFactoryBean.setWhereClause("where human_readable_number is null and expiration_date is null");
    sqlPagingQueryProviderFactoryBean.setSortKeys(sortKey);
    return sqlPagingQueryProviderFactoryBean.getObject();
  }

}
