class SudokuSolver {

  public static void multiple_solver(int d, int h, int m) {
    int solved = 0;

    for (int i = 0; i < m; i++) {
      SudokuCube puzzle = new SudokuCube(d, h);
      System.out.println(". . . Processing problem #"+(i+1));
      puzzle.solve();
      if (puzzle.possible) {
        System.out.println(puzzle.toString());
        solved++;
      }
    }
    double percent = (double)solved / (double)m;
    String ans = "\n\tDimension: " + d;
    ans += "\n\tSpace Factor: "+h+"\n\tTotal: "+m;
    ans += "\n\tSolved: "+solved+" ("+(percent*100)+"%)";
    System.out.println(ans);
  }


  public static void main(String[] args) {

    int dimension = 9;
    int space_factor = 2;
    int multiples = 1;

    // whole bunch of optional input stuff - defaults are above
    // arg1: board dimension (must be a perfect square, default 9)
    // arg2: number of puzzles to generate
    // arg3: factor for amount of blank spaces on starter board
    try {
      dimension = Integer.parseInt(args[0]);
      int rt = (int)Math.sqrt(dimension);
      if (rt*rt != dimension) {
       throw new Exception();
      }
      try {
        multiples = Integer.parseInt(args[1]);
      } catch (Exception e) {
        multiples = 1;
      }
      try {
        space_factor = Integer.parseInt(args[2]);
      } catch (Exception e) {
        space_factor = 2;
      }
    }
    catch (Exception e) {
      dimension = 9;
    }

    // solve all the shit
    multiple_solver(dimension, space_factor, multiples);


  } // end of main
}
