package com.coderdkk.creditcard.fake.generation;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "finance.credit-card.fake.job.enabled", havingValue = "true")
@RequiredArgsConstructor
public class FakeCreditCardJobLauncher {
  private final JobLauncher jobLauncher;
  private final Job fakeCreditCardJob;

  @Scheduled(cron = "0 */1 * * * ?")
  public void perform() throws Exception {
    JobParameters params = new JobParametersBuilder()
            .addString("JobID", String.valueOf(System.currentTimeMillis()))
            .toJobParameters();

    jobLauncher.run(fakeCreditCardJob, params);
  }
}
