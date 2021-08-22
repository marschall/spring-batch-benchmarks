package com.github.marschall.spring.batch.benchmarks.configuration;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableBatchProcessing
@Import({
  H2DataSourceConfiguration.class,
  BenchmarkJobConfiguration.class})
public class MapConfiguration {

  @Bean
  public BatchConfigurer batchConfigurer() {
    return new DefaultBatchConfigurer(null);
  }

}
