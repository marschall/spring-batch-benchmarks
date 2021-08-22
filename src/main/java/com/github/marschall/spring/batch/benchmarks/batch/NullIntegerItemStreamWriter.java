package com.github.marschall.spring.batch.benchmarks.batch;

import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamWriter;

public class NullIntegerItemStreamWriter implements ItemStreamWriter<Integer> {

  @Override
  public void open(ExecutionContext executionContext) {
    // not state

  }

  @Override
  public void update(ExecutionContext executionContext) {
    // not state
  }

  @Override
  public void close() {
    // not state
  }

  @Override
  public void write(List<? extends Integer> items) {
    // intentionally left empty
  }

}
