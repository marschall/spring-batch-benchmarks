package com.github.marschall.spring.batch.benchmarks.configuration;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.marschall.spring.batch.benchmarks.batch.MapBatchConfigurer;

@Configuration
@EnableBatchProcessing
@Import({
  H2DataSourceConfiguration.class,
  BenchmarkJobConfiguration.class})
public class MapConfiguration {

  @Bean
  public BatchConfigurer batchConfigurer() {
    try {
      return new MapBatchConfigurer();
    } catch (Exception e) {
      throw new BeanCreationException("could not create batch configurer", e);
    }
  }

}
