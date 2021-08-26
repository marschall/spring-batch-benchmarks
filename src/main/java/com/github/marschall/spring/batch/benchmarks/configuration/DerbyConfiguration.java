package com.github.marschall.spring.batch.benchmarks.configuration;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableBatchProcessing
@Import({
  DerbyDataSourceConfiguration.class,
  BenchmarkJobConfiguration.class})
public class DerbyConfiguration {

}
