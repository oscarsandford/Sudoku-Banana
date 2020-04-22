class SudokuSolver {

  public static void main(String[] args) {

    int dimension = 9;
    SudokuCube puzzle = new SudokuCube(dimension);

    System.out.println("Puzzle:\n" + puzzle.toString());
    puzzle.solve_sudoku();
    System.out.println("Solved:\n" + puzzle.toString());

  }
}
