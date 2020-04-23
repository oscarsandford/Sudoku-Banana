public class SudokuCube {

  public int[][] cube;
  public int dim;
  private int dim_rt;
  public boolean possible = true;
  private int space_factor;   // ideally 2

  public SudokuCube(int n, int s) {
    dim = n;
    dim_rt = (int)Math.sqrt(dim);
    cube = new int[dim][dim];
    space_factor = s;
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
    int num = random_hint(0, space_factor*dim);
    // refresh if num invalid, good if 0
    while (invalid_num(i, j, num) && num != 0) {
      num = random_hint(0, space_factor*dim);
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

  /* I got this function from the internet because I was frustrated,
    but it turns out my problem was not generating enough puzzles
    because my hint generation makes some of them impossible. Yikes.*/
  public boolean solve_sudoku() {
    int r = -1;
    int c = -1;
    boolean is_empty = true;

    // this is shit to do every time
    for (int i = 0; i < dim; i++) {
      for (int j = 0; j < dim; j++) {
        if (cube[i][j] == 0) {
          r = i;
          c = j;
          is_empty = false;
          break;
        }
      }
      if (!is_empty) {
        break;
      }
    }
    // we done baby let's go
    if (is_empty) {
      return true;
    }

    for (int num = 1; num <= dim; num++) {
      if (!invalid_num(r, c, num)) {
        cube[r][c] = num;
        // if possible to solve this, return true
        if (solve_sudoku()) {
          return true;
        }
        // else make this slot 0 and try again later
        else {
          cube[r][c] = 0;
        }
      }
    }
    // holy shoot kill me
    return false;
  }

  /* solver function helper so I can try out some other methods
    of solving the problem using backtracking */
  public void solve() {
    if (!solve_sudoku()) {
      possible = false;
    }
  }

  // returns the 2D puzzle board in its current state, awful syntax
  public String toString() {
    int max_num_len = (dim + "").length();
    String line = new String(new char[ (dim_rt*(dim_rt+1))+(dim_rt+1)+(dim*max_num_len) ]).replace("\0", "-") + "\n";
    String s = line;

    for (int i = 0; i < dim; i++) {
      for (int j = 0; j < dim; j++) {
        int num_len = (cube[i][j] + "").length();
        String offset = new String(new char[max_num_len-num_len]).replace("\0", " ");

        if (j % dim_rt == 0) {
          s += "| ";
        }
        // print a blank space if there's a 0
        if (cube[i][j] == 0) {
          s+= new String(new char[max_num_len+1]).replace("\0", " ");
        }
        else {
          s += offset + cube[i][j] + " ";
        }
      }
      s += "|\n";
      if ((i+1) % dim_rt == 0)
         s += line;
    }
    return s;
  }

}
