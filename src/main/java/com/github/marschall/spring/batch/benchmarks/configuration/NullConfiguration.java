package com.github.marschall.spring.batch.benchmarks.configuration;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.marschall.spring.batch.inmemory.NullBatchConfiguration;

@Configuration
@EnableBatchProcessing
@Import({
  NullBatchConfiguration.class,
  BenchmarkJobConfiguration.class})
public class NullConfiguration {

}
