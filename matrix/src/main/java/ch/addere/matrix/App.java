package ch.addere.matrix;

import ch.addere.world.World;
import ch.addere.world.WorldImpl;

public class App {

  public static void main(String[] args) {
    World world = new WorldImpl();
    Matrix matrix = new MatrixImpl(world);
    matrix.initialise();
  }
}
