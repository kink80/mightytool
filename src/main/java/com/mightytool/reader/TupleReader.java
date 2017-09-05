package com.mightytool.reader;

import com.mightytool.factory.Deployable;
import com.mightytool.writer.TupleWriter;
import com.typesafe.config.Config;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Reads a tuple and passes it onto a writer
 */
public abstract class TupleReader implements Deployable {

  public static final String CONFIG = "mightytool.reader";
  public static final String PARALLELISM = "parallelism";
  public static final String BEAN_NAME = "beanname";

  private final Config writerConfig;

  @Autowired
  public TupleReader(Config writerConfig) {
    this.writerConfig = writerConfig;
  }

  public abstract void read(TupleWriter writer);

}
