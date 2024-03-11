package ch.addere.world;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorldImpl implements World {

  private static final Logger LOGGER = LoggerFactory.getLogger(WorldImpl.class);

  @Override
  public void initialise() {
    LOGGER.info("Initialise World...");
      try {
          Thread.sleep(1300);
      } catch (InterruptedException e) {
          throw new RuntimeException(e);
      }
      LOGGER.info("World initialised!");
  }
}
