package com.mightytool.factory;

import com.typesafe.config.Config;
import org.springframework.context.ApplicationContext;

public class LocalDeploymentFactory implements DeploymentFactory {

  private final ApplicationContext ctx;

  public LocalDeploymentFactory(ApplicationContext ctx) {
    this.ctx = ctx;
  }

  @Override
  public <T extends Deployable> T deploy(String elementName, Config config) {
    return (T) ctx.getBean(elementName, config);
  }
}
