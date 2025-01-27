
public class Simtext extends Simulation{

  private static void text(Grassland mead) {
    if (mead != null) {
      int width = mead.width();
      int height = mead.height();

      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          int contents = mead.cellContents(x, y);
          if (contents == Grassland.RABBIT){
            System.out.println("R");
            //if(){}
          } else if (contents == Grassland.CARROT) {
            System.out.println("C");
          } else {
            System.out.println("E");
          }
        }
      }
    }
  }

  public static void main(String[] argv) throws InterruptedException {
    Grassland mea;

    if (argv.length > 0) {
      try {
        i = Integer.parseInt(argv[0]);
      }
      catch (NumberFormatException e) {
        System.out.println("First argument to Simulation is not an number.");
      }
    }

    if (argv.length > 1) {
      try {
        j = Integer.parseInt(argv[1]);
      }
      catch (NumberFormatException e) {
        System.out.println("Second argument to Simulation is not an number.");
      }
    }

    if (argv.length > 2) {
      try {
        starveTime = Integer.parseInt(argv[2]);
      }
      catch (NumberFormatException e) {
        System.out.println("Third argument to Simulation is not an number.");
      }
    }

    mea = new Grassland(i, j, starveTime);
    rand_insert(mea);

    while (true) {                                              // Loop forever
      Thread.sleep(1000);                // Wait one second (1000 milliseconds)
      text(mea);                       // Draw the current meadow
      mea = mea.timeStep();                              // Simulate a timestep
    }
  }

}

