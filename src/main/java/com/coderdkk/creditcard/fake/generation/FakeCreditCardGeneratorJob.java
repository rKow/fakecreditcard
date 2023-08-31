package com.coderdkk.creditcard.fake.generation;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;


@Component
@RequiredArgsConstructor
public class FakeCreditCardGeneratorJob {

  @Value("${finance.credit-card.fake.job.chunk-size:20}")
  private Integer chunkSize;

  @Bean
  public TaskExecutor taskExecutor() {
    SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor("spring_batch_fake_credit_card");
    simpleAsyncTaskExecutor.setConcurrencyLimit(10);
    simpleAsyncTaskExecutor.setThreadNamePrefix("fakeCreditCardProcessAsyncThreads");
    return simpleAsyncTaskExecutor;
  }

  @Bean
  public Job fakeCreditCardJob(
          @Qualifier("generateNumberAndExpirationDateFlow") Flow generateNumberAndExpirationDateFlow
          , JobRepository jobRepository) {
    return new JobBuilder("job", jobRepository)
            .start(generateNumberAndExpirationDateFlow)
            .build()
            .build();
  }

  @Bean
  public Flow generateNumberAndExpirationDateFlow(
          @Qualifier("createEmptyCreditCardStep") Step createEmptyCreditCardStep
  ) {
    return new FlowBuilder<SimpleFlow>("generateNumberAndExpirationDateFlow")
            .start(createEmptyCreditCardStep)
            .build();
  }

  @Bean
  public Step createEmptyCreditCardStep(
          @Qualifier("fakeCreditCardItemReader") ItemReader<CreditCardItem> fakeCreditCardItemReader
          , FakeCreditCardItemProcessor fakeCreditCardItemProcessor
          , JobRepository jobRepository
          , PlatformTransactionManager transactionManager
          , TaskExecutor taskExecutor
          , FakeCreditCardResultWriter fakeCreditCardResultWriter) {
    return new StepBuilder("generateNumberStep", jobRepository)
            .<CreditCardItem, FakeCreditCardResult>chunk(chunkSize, transactionManager)
            .reader(fakeCreditCardItemReader)
            .processor(fakeCreditCardItemProcessor)
            .writer(fakeCreditCardResultWriter)
            .taskExecutor(taskExecutor)
            .build();
  }

}
