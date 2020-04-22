public class SudokuCube {

  public int[][] cube;
  public int dim;
  public int dim_rt;
  private int hint_rarity_factor = 1;   // ideally 2

  public SudokuCube(int n) {
    dim = n;
    dim_rt = (int)Math.sqrt(dim);
    cube = new int[dim][dim];
    create_sudoku();
  }

  // finds the top-left corner "global" index of the current grid
  private int find_start_index(int ind) {
    int offset;
    for (offset = 0; (ind-offset) % dim_rt != 0; offset++);
    return ind-offset;
  }

  // randoms greater than dim converted to 0's
  private int random_hint(int min, int max) {
    int num = (int)(Math.random() * (max-min + 1) + min);
    if (num > dim) num = 0;
    return num;
  }

  // checks if a number in within the smaller grid, or the row/col
  private boolean invalid_num(int i, int j, int num) {
    // return true if number in column
    for (int c = 0; c < dim; c++) {
      if (cube[i][c] == num) {
        return true;
      }
    }
    // return true if number in row
    for (int r = 0; r < dim; r++) {
      if (cube[r][j] == num) {
        return true;
      }
    }
    // get position at top-left of current grid in "global" coords
    int row_start_global = find_start_index(i);
    int col_start_global = find_start_index(j);
    // return true if number in grid, local row/col is dim_rt long
    for (int r = 0; r < dim_rt; r++) {
      for (int c = 0; c < dim_rt; c++) {
        if (cube[row_start_global+r][col_start_global+c] == num) {
          return true;
        }
      }
    }
    // else return false
    return false;
  }

  // return a good number for ij-index, 0's are blank spaces
  private int create_slot(int i, int j) {
    int num = random_hint(0, hint_rarity_factor*dim);
    // refresh if num invalid, good if 0
    while (invalid_num(i, j, num) && num != 0) {
      num = random_hint(0, hint_rarity_factor*dim);
    }
    return num;
  }

  // solves the number for blank spaces
  private int solve_slot(int i, int j) {
    int num = random_hint(1, dim);
    System.out.println("Solving slot [" + i + "," + j + "] . . .");
    // !!! this won't work grr
    /* It can't find a good num when there's a bunch of different options
      It just gets stuck ..
      I think we need a separate method to help invalid_num */

    while(invalid_num(i, j, num)) {
      num = random_hint(1, dim);
    }

    return num;
  }

  // create the puzzle, public because why not
  public void create_sudoku() {
    for (int i = 0; i < dim; i++) {
      for (int j = 0; j < dim; j++) {
        cube[i][j] = create_slot(i, j);
      }
    }
  }

  // solve the puzzle
  public void solve_sudoku() {
    for (int i = 0; i < dim; i++) {
      for (int j = 0; j < dim; j++) {
        if (cube[i][j] == 0) {
          cube[i][j] = solve_slot(i, j);
        }
      }
    }
  }

  // returns the 2D puzzle board in its current state
  public String toString() {
    String s = "-------------------------\n";
    for (int i = 0; i < dim; i++) {
      for (int j = 0; j < dim; j++) {
        if (j % dim_rt == 0) {
          s += "| ";
        }
        // print a black space if there's a 0
        if (cube[j][i] == 0) {
          s+= "  ";
        }
        else {
          s += cube[j][i] + " ";
        }
      }
      s += "|\n";
      if ((i+1) % dim_rt == 0)
         s += "-------------------------\n";
    }
    return s;
  }

}
