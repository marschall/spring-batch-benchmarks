package com.github.marschall.spring.batch.benchmarks.batch;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.PeekableItemReader;

public class IntegerStreamItemReader implements ItemStreamReader<Integer>, PeekableItemReader<Integer> {

  private static final String END_KEY = "IntStreamItemReader.end";

  private static final String CURRENT_KEY = "IntStreamItemReader.current";

  private int current;

  private int end;

  public IntegerStreamItemReader(int current, int end) {
    this.current = current;
    this.end = end;
  }

  @Override
  public void open(ExecutionContext executionContext) throws ItemStreamException {
    if (executionContext.containsKey(CURRENT_KEY)) {
      this.current = executionContext.getInt(CURRENT_KEY);
    }
    if (executionContext.containsKey(END_KEY)) {
      this.end = executionContext.getInt(END_KEY);
    }
  }

  @Override
  public void update(ExecutionContext executionContext) {
    executionContext.putInt(CURRENT_KEY, this.current);
    executionContext.putInt(END_KEY, this.end);
  }

  @Override
  public void close() throws ItemStreamException {
    this.current = this.end;
  }

  @Override
  public Integer read() {
    if (this.current < this.end) {
      return this.current++;
    } else {
      return null;
    }
  }

  @Override
  public Integer peek() {
    if (this.current < this.end) {
      return this.current;
    } else {
      return null;
    }
  }

}
