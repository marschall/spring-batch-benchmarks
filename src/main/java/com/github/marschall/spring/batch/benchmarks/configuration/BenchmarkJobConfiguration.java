package com.github.marschall.spring.batch.benchmarks.configuration;

import java.util.function.Function;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.support.SimplePartitioner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.marschall.spring.batch.benchmarks.batch.IntegerStreamItemReader;
import com.github.marschall.spring.batch.benchmarks.batch.NullIntegerItemStreamWriter;

@Configuration
public class BenchmarkJobConfiguration {

  private static final int PARTITION_COUNT = 5_000;

  private static final int CHUNK_SIZE = 10;

  @Autowired
  public JobBuilderFactory jobBuilderFactory;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;

  @Bean
  public Job benchmarkJob() {
    return this.jobBuilderFactory.get("benchmarkJob")
      .incrementer(new RunIdIncrementer())
      .start(this.partionedBenchmarkStep())
      .build();
  }

  @Bean
  public Step partionedBenchmarkStep() {
    return this.stepBuilderFactory.get("partionedBenchmarkStep")
            .partitioner("benchmarkStep", new SimplePartitioner())
            .step(this.benchmarkStep())
            .gridSize(5_000)
            .build();
  }

  @Bean
  public Step benchmarkStep() {
    return this.stepBuilderFactory.get("benchmarkStep")
      .<Integer, Integer>chunk(CHUNK_SIZE)
      .reader(new IntegerStreamItemReader(0, PARTITION_COUNT * CHUNK_SIZE))
      .processor(Function.identity())
      .writer(new NullIntegerItemStreamWriter())
      .build();
  }

}
