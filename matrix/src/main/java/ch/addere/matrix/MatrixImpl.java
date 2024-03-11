package ch.addere.matrix;

import ch.addere.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatrixImpl implements Matrix {

  private static final Logger logger = LoggerFactory.getLogger(MatrixImpl.class);

  private final World world;

  public MatrixImpl(World world) {
    this.world = world;
  }

  @Override
  public void initialise() {
    world.initialise();
    logger.info("Initialising the matrix...");
    logger.info("Matrix initialised!");
  }
}
