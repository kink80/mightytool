package com.mightytool.factory;

import com.typesafe.config.Config;

/**
 * Provides deployment of Deployable elements
 */
public interface DeploymentFactory {

  /**
   * Deploys target element onto a target and return reference to it
   */
  <T extends Deployable> T deploy(String elementName, Config config);

}
