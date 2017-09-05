package com.mightytool;

import com.mightytool.factory.DeploymentFactory;
import com.mightytool.factory.LocalDeploymentFactory;
import com.mightytool.reader.TupleReader;
import com.mightytool.router.TupleRouter;
import com.mightytool.writer.TupleWriter;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class MightyTool {

  public static void main(String[] args) {
    SpringApplication.run(MightyTool.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    return args -> {
      Config toolConfig = ConfigFactory.parseFile(new File(args[0]));
      DeploymentFactory deploymentFactory = new LocalDeploymentFactory(ctx);

      Config writerConfig = toolConfig.getConfig(TupleWriter.CONFIG);
      String writerBean = writerConfig.getString(TupleWriter.BEAN_NAME);
      List<TupleWriter> writers = IntStream.of(writerConfig.getInt(TupleWriter.PARALLELISM))
          .mapToObj(i -> (TupleWriter) deploymentFactory.deploy(writerBean, writerConfig))
          .collect(Collectors.toList());


      TupleRouter router = new TupleRouter(writers, toolConfig.getConfig(TupleRouter.CONFIG));

      Config readerConfig = toolConfig.getConfig(TupleReader.CONFIG);
      ExecutorService readerExecutorService = Executors.newFixedThreadPool(readerConfig.getInt(TupleReader.PARALLELISM));

      CompletableFuture[] all = IntStream.of(readerConfig.getInt(TupleReader.PARALLELISM))
          .mapToObj(i -> (Runnable) () -> {
            TupleReader rdr = deploymentFactory.deploy(readerConfig.getString(TupleReader.BEAN_NAME), readerConfig);
            rdr.read(router);
          })
          .map(r -> CompletableFuture.runAsync(r, readerExecutorService))
          .toArray(CompletableFuture[]::new);

      try {
        CompletableFuture.allOf(all).join();
      } catch (CompletionException | CancellationException ex) {
        ex.printStackTrace();
      }

    };
  }

}
