package com.github.marschall.spring.batch.benchmarks;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.github.marschall.spring.batch.benchmarks.configuration.MapConfiguration;

public class ApplicationMain {

  public static void main(String[] args) throws Exception {
  Class<?> configurationClass = null;
//  configurationClass = InMemoryConfiguration.class;
//  configurationClass = NullConfiguration.class;
  configurationClass = MapConfiguration.class;
//  configurationClass = H2Configuration.class;
  try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(configurationClass)) {
    JobLauncher jobLauncher = applicationContext.getBean(JobLauncher.class);
    Job job = applicationContext.getBean("benchmarkJob", Job.class);
    JobParameters jobParameters = new JobParameters();
    jobLauncher.run(job, jobParameters);
  }
  }

}
