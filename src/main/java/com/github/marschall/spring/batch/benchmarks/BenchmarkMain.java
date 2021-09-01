package com.github.marschall.spring.batch.benchmarks;

import java.io.IOException;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkMain {

  public static void main(String[] args) throws RunnerException, IOException, Exception {
    Options options = new OptionsBuilder()
//        .include("com\\.github\\.marschall\\.spring\\.batch\\.benchmarks\\..*Benchmarks")
        .include("com\\.github\\.marschall\\.spring\\.batch\\.benchmarks\\.JobKeyGeneratorBenchmarks")
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
