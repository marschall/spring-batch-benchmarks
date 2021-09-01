package com.github.marschall.spring.batch.benchmarks;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.github.marschall.spring.batch.benchmarks.configuration.DerbyConfiguration;
import com.github.marschall.spring.batch.benchmarks.configuration.H2Configuration;
import com.github.marschall.spring.batch.benchmarks.configuration.InMemoryConfiguration;
import com.github.marschall.spring.batch.benchmarks.configuration.MapConfiguration;
import com.github.marschall.spring.batch.benchmarks.configuration.NullConfiguration;

public class ApplicationMain {

  public static void main(String[] args) throws Exception {
    Class<?> configurationClass = null;
//    configurationClass = InMemoryConfiguration.class;
//      configurationClass = NullConfiguration.class;
//    configurationClass = MapConfiguration.class;
//    configurationClass = DerbyConfiguration.class;
      configurationClass = H2Configuration.class;
    try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(configurationClass)) {
      JobLauncher jobLauncher = applicationContext.getBean(JobLauncher.class);
      Job job = applicationContext.getBean("benchmarkJob", Job.class);
      JobParameters jobParameters = new JobParameters();
      jobLauncher.run(job, jobParameters);
      jobLauncher.run(job, new JobParametersBuilder().addLong("key", 1L).toJobParameters());
    }
  }

}
