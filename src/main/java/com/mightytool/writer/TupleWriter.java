package com.mightytool.writer;

import com.mightytool.data.Tuple;
import com.mightytool.factory.Deployable;
import com.typesafe.config.Config;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Processes a single tuple
 */
public abstract class TupleWriter implements Deployable {

  public static final String CONFIG = "mightytool.writer";
  public static final String PARALLELISM = "parallelism";
  public static final String BEAN_NAME = "beanname";

  private final Config writerConfig;

  @Autowired
  public TupleWriter(Config writerConfig) {
    this.writerConfig = writerConfig;
  }

  public abstract void process(Tuple tuple);

}
