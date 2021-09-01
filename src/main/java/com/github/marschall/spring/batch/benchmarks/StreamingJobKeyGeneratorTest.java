package com.github.marschall.spring.batch.benchmarks;

import org.springframework.batch.core.DefaultJobKeyGenerator;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

public class StreamingJobKeyGeneratorTest {

  public static void main(String[] args) {
    JobParameters jobParameters = new JobParametersBuilder()
        .addLong("key", 1L)
        .toJobParameters();

    System.out.println(new DefaultJobKeyGenerator().generateKey(jobParameters));
    System.out.println(new StreamingJobKeyGenerator().generateKey(jobParameters));
  }

}
