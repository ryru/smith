package ch.addere.matrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatrixImpl implements Matrix {

  private static final Logger logger = LoggerFactory.getLogger(MatrixImpl.class);

  @Override
  public void initialise() {
    logger.info("Initialising the matrix...");
    logger.info("Matrix initialised!");
  }
}
