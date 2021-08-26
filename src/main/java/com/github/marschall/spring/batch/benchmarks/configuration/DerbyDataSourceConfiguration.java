package com.github.marschall.spring.batch.benchmarks.configuration;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.DERBY;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

@Configuration
public class DerbyDataSourceConfiguration {

  @Bean
  public DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
            .setType(DERBY)
            .generateUniqueName(true)
            .setScriptEncoding("UTF-8")
            .addScript("/org/springframework/batch/core/schema-derby.sql")
            .build();
  }

}
