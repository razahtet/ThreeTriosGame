package customer.gamefeatures;

/**
 * The game grid of the game, stores the row and col size of the game,
 * a 2D-array that stores all the cards being put in game grid.
 * Use a grid configuration file to initialize the game gird,
 * which is to set up all the card cells/ holes before any card
 * be played to grid.
 */
public class GameGrid implements IGameGrid {
  private final int row;
  private final int col;
  private int cells;
  private int holes;
  private Card[][] grid;

  /**
   * The constructor of the game grid.
   *
   * @param row  The number of row of the grid.
   * @param col  The number of col of the grid.
   * @param grid The 2D-array represent the card.
   */
  public GameGrid(int row, int col, Card[][] grid, int cells) {
    // check whether the number of cells is odd
    if (cells % 2 == 0) {
      throw new IllegalArgumentException("The number of card cells should be odd.");
    }
    this.row = row;
    this.col = col;
    this.grid = grid;
    this.cells = cells;
  }

  /**
   * The constructor of the game grid that copies another game grid.
   *
   * @param another The game grid that is getting copied.
   */
  public GameGrid(GameGrid another) {
    this.row = another.row;
    this.col = another.col;
    this.grid = new Card[another.row][another.col];
    for (int i = 0; i < another.grid.length; i++) {
      for (int j = 0; j < another.grid[i].length; j++) {
        this.grid[i][j] = new GameCard((GameCard) another.grid[i][j]);
      }
    }
    this.cells = another.cells;
  }

  @Override
  public Card[][] getGrid() {
    return this.grid;
  }

  @Override
  public String toString() {
    String stringGrid = "";
    for (int row = 0; row < this.row; row += 1) {
      String line = "";
      for (int col = 0; col < this.col; col += 1) {
        // empty cells
        if (this.grid[row][col].getName().equals("C")) {
          line = line + "_";
        } else if (this.grid[row][col].getName().equals("X")) { // hole
          line = line + " ";
        } else { // normal game card
          line = line + this.grid[row][col].getColor().charAt(0);
        }
      }
      // check whether it is the last row
      if (row == this.row - 1) {
        stringGrid = stringGrid + line;
      } else {
        stringGrid = stringGrid + line + "\n";
      }
    }
    return stringGrid;
  }

  @Override
  public void playToGrid(Card card, int x, int y) {
    // check if the given x index is board.
    if (x < 0 || x > col - 1) {
      throw new IllegalArgumentException("Invalid x index: " + x);
    }
    // check if the given y index is board.
    if (y < 0 || y > row - 1) {
      throw new IllegalArgumentException("Invalid y index: " + y);
    }
    // check if the position is a card cell able to play card to
    // x represent col, y represent row
    if (grid[y][x].getName().equals("C")) {
      grid[y][x] = card;
      cells -= 1; // decrease the available cell numbers
    } else {
      throw new IllegalArgumentException("Cards could only be played to empty card cells.");
    }
  }

  @Override
  public boolean hasCard(int x, int y) {
    // if x and y exceed the game board, return false
    if (x < 0 || x > col - 1 || y < 0 || y > row - 1) {
      return false;
    }
    // check if grid[y][x] is neither empty cell nor hole
    return !grid[y][x].getName().equals("X")
            &&
            !grid[y][x].getName().equals("C");
  }

  @Override
  public int getCells() {
    return this.cells;
  }

  @Override
  public int getRow() {
    return this.row;
  }

  @Override
  public int getCol() {
    return this.col;
  }

  @Override
  public IGameGrid copy() {
    Card[][] copyGrid = new Card[this.row][this.col];
    int cells = getCells();
    if (cells % 2 == 0) {
      cells += 1;
    }
    GameGrid copy = new GameGrid(getRow(), getCol(), copyGrid, cells);
    if (this.grid != null) {
      copy.grid = new Card[this.row][this.col];
      for (int row = 0; row < this.row; row += 1) {
        for (int col = 0; col < this.col; col += 1) {
          if (this.grid[row][col] != null) {
            copy.grid[row][col] = this.grid[row][col].copy();
          }
        }
      }
    }
    return copy;
  }

  @Override
  public String sizeOfGrid() {
    return row + " x " + col;
  }
}
