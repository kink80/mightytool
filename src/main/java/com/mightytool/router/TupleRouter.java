package com.mightytool.router;

import com.mightytool.data.Tuple;
import com.mightytool.writer.TupleWriter;
import com.typesafe.config.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Routes tuples to multiple writers
 */
public class TupleRouter extends TupleWriter {

  public static final String CONFIG = "mightytool.router";
  public static final String NAME = "TUPLE_ROUTER";

  private AtomicInteger currentRoutee = new AtomicInteger(0);
  private final List<TupleWriter> writerList = Collections.synchronizedList(new ArrayList<>());
  private final Integer mod;

  public TupleRouter(List<TupleWriter> writerList, Config routerConfig) {
    super(routerConfig);
    this.writerList.addAll(writerList);
    mod = this.writerList.size();
  }

  @Override
  public void process(Tuple tuple) {
    int routee = currentRoutee.incrementAndGet() % mod;
    writerList.get(routee).process(tuple);
  }
}
