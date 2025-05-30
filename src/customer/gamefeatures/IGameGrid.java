package customer.gamefeatures;

/**
 * Interface for the game grid class.
 */
public interface IGameGrid {
  /**
   * Get the grid of the card grid.
   *
   * @return The grid of this game.
   */
  Card[][] getGrid();

  /**
   * Output the game grid in String format.
   *
   * @return Grid state in the format of String.
   */
  String toString();

  /**
   * Play a card from the player's hand card into the given grid, update the whole game grid.
   *
   * @param card The gird be played to.
   * @param x    The col coordinate of the position that the player want to play card to.
   * @param y    The row coordinate of the position that the player want to play card to.
   */
  void playToGrid(Card card, int x, int y);

  /**
   * Check whether there is an actual game card exists at this position.
   *
   * @param x The col coordinate of the position.
   * @param y The row coordinate of the position.
   * @return Whether there is game card exist at this position.
   */
  boolean hasCard(int x, int y);

  /**
   * Get the number of available card cells.
   *
   * @return The cell number left.
   */
  int getCells();

  /**
   * Get the row number of the game grid.
   *
   * @return The col number of the game grid.
   */

  int getRow();

  /**
   * Get the col number of the game grid.
   *
   * @return The col number of the game grid.
   */
  int getCol();

  /**
   * Copy the current GameGrid.
   *
   * @return A copy of the current game grid.
   */
  IGameGrid copy();

  /**
   * Returns the size of the grid in a string with the number of rows and columns.
   *
   * @return The number of rows and columns.
   */
  String sizeOfGrid();
}
