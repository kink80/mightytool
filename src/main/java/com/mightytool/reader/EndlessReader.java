package com.mightytool.reader;

import com.mightytool.data.Tuple;
import com.mightytool.writer.TupleWriter;
import com.typesafe.config.Config;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service(EndlessReader.NAME)
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EndlessReader extends TupleReader {

  public static final String NAME = "EMPTY_READER";

  public EndlessReader(Config writerConfig) {
    super(writerConfig);
  }

  @Override
  public void read(TupleWriter writer) {
    while(true) {
      writer.process(new Tuple());
    }
  }
}
