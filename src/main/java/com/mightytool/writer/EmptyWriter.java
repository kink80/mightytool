package com.mightytool.writer;

import com.mightytool.data.Tuple;
import com.typesafe.config.Config;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service(EmptyWriter.NAME)
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EmptyWriter extends TupleWriter {

  public static final String NAME = "EMPTY_WRITER";

  public EmptyWriter(Config writerConfig) {
    super(writerConfig);
  }

  @Override
  public void process(Tuple tuple) {
    System.out.println(tuple);
  }
}
