package com.github.marschall.spring.batch.benchmarks.configuration;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

@Configuration
public class H2DataSourceConfiguration {

  @Bean
  public DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
            .setType(H2)
            .generateUniqueName(true)
            .setScriptEncoding("UTF-8")
//            .addScript("/org/springframework/batch/core/schema-drop-h2.sql")
            .addScript("/org/springframework/batch/core/schema-h2.sql")
            .build();
  }

}
