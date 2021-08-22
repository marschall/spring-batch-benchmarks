package com.github.marschall.spring.batch.benchmarks;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.openjdk.jmh.annotations.Mode.Throughput;
import static org.openjdk.jmh.annotations.Scope.Benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.github.marschall.spring.batch.benchmarks.configuration.H2Configuration;
import com.github.marschall.spring.batch.benchmarks.configuration.InMemoryConfiguration;
import com.github.marschall.spring.batch.benchmarks.configuration.MapConfiguration;
import com.github.marschall.spring.batch.benchmarks.configuration.NullConfiguration;


@BenchmarkMode(Throughput)
@OutputTimeUnit(MILLISECONDS)
@State(Benchmark)
public class RepositoryBenchmarks {

  private static final String REPOSITORY_TYPE_IN_MEMORY = "in-memory";

  private static final String REPOSITORY_TYPE_MAP = "map";

  private static final String REPOSITORY_TYPE_H2 = "h2";

  private static final String REPOSITORY_TYPE_NULL = "null";

  @Param({REPOSITORY_TYPE_H2, REPOSITORY_TYPE_IN_MEMORY, REPOSITORY_TYPE_NULL})
  String repositoryType;

  private AnnotationConfigApplicationContext applicationContext;
  private JobLauncher jobLauncher;
  private Job job;

  private JobParameters jobParameters;

  @Setup
  public void doSetup() {
    Class<?> configurationClass = null;
    switch (this.repositoryType) {
      case REPOSITORY_TYPE_IN_MEMORY:
        configurationClass = InMemoryConfiguration.class;
        break;
      case REPOSITORY_TYPE_NULL:
        configurationClass = NullConfiguration.class;
        break;
      case REPOSITORY_TYPE_MAP:
        configurationClass = MapConfiguration.class;
        break;
      case REPOSITORY_TYPE_H2:
        configurationClass = H2Configuration.class;
        break;
    }
    this.applicationContext = new AnnotationConfigApplicationContext(configurationClass);
    this.jobLauncher = this.applicationContext.getBean(JobLauncher.class);
    this.job = this.applicationContext.getBean("benchmarkJob", Job.class);
    this.jobParameters = new JobParameters();
  }

  @TearDown
  public void doTearDown() {
    this.applicationContext.close();
  }


  @Benchmark
  public JobExecution launchJobH2() throws JobExecutionException {
    return this.jobLauncher.run(this.job, this.jobParameters);
  }

}
