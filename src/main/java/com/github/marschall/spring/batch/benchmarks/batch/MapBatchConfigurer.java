package com.github.marschall.spring.batch.benchmarks.batch;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

public class MapBatchConfigurer implements BatchConfigurer {

  private final PlatformTransactionManager transactionManager;
  private final JobRepository jobRepository;
  private final JobLauncher jobLauncher;
  private final JobExplorer jobExplorer;

  public MapBatchConfigurer() throws Exception {
    this.transactionManager = new ResourcelessTransactionManager();
    MapJobRepositoryFactoryBean jobRepositoryFactory = new MapJobRepositoryFactoryBean(this.getTransactionManager());
    jobRepositoryFactory.afterPropertiesSet();
    this.jobRepository = jobRepositoryFactory.getObject();

    MapJobExplorerFactoryBean jobExplorerFactory = new MapJobExplorerFactoryBean(jobRepositoryFactory);
    jobExplorerFactory.afterPropertiesSet();
    this.jobExplorer = jobExplorerFactory.getObject();

    this.jobLauncher = this.createJobLauncher();
  }

  @Override
  public JobRepository getJobRepository() {
    return this.jobRepository;
  }

  @Override
  public PlatformTransactionManager getTransactionManager() {
    return this.transactionManager;
  }

  @Override
  public JobLauncher getJobLauncher() {
    return this.jobLauncher;
  }

  @Override
  public JobExplorer getJobExplorer() {
    return this.jobExplorer;
  }

  private JobLauncher createJobLauncher() throws Exception {
    SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
    jobLauncher.setJobRepository(this.jobRepository);
    jobLauncher.afterPropertiesSet();
    return jobLauncher;
  }

}