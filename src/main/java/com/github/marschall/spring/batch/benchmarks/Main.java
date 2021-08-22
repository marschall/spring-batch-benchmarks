package com.github.marschall.spring.batch.benchmarks;

import java.io.IOException;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class Main {

  public static void main(String[] args) throws RunnerException, IOException, Exception {
//    Class<?> configurationClass = null;
//    configurationClass = InMemoryConfiguration.class;
//    configurationClass = NullConfiguration.class;
//    configurationClass = MapConfiguration.class;
//    configurationClass = H2Configuration.class;
//    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(configurationClass);
//    JobLauncher jobLauncher = applicationContext.getBean(JobLauncher.class);
//    Job job = applicationContext.getBean("benchmarkJob", Job.class);
//    JobParameters jobParameters = new JobParameters();
//    jobLauncher.run(job, jobParameters);

    Options options = new OptionsBuilder()
        .include("com\\.github\\.marschall\\.spring\\.batch\\.benchmarks\\..*Benchmarks")
        .forks(1)
        .warmupIterations(3)
        .measurementIterations(5)
        .resultFormat(ResultFormatType.CSV)
        //.addProfiler("gc")
        .addProfiler("jfr", "debugNonSafePoints=true")
        .build();
    new Runner(options).run();

  }

}
