package com.github.marschall.spring.batch.benchmarks.configuration;

import java.util.function.Function;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.marschall.spring.batch.benchmarks.batch.IntegerStreamItemReader;
import com.github.marschall.spring.batch.benchmarks.batch.NullIntegerItemStreamWriter;

@Configuration
public class BenchmarkJobConfiguration {

  private static final int CHUNK_SIZE = 10;

  @Autowired
  public JobBuilderFactory jobBuilderFactory;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;

  @Bean
  public Job benchmarkJob() {
    return this.jobBuilderFactory.get("benchmarkJob")
      .incrementer(new RunIdIncrementer())
      .start(this.benchmarkStep())
      .build();
  }

  @Bean
  public Step benchmarkStep() {
    return this.stepBuilderFactory.get("benchmarkStep")
      .<Integer, Integer>chunk(10)
      .reader(new IntegerStreamItemReader(0, 5_000 * CHUNK_SIZE))
      .processor(Function.identity())
      .writer(new NullIntegerItemStreamWriter())
      .build();
  }

}
