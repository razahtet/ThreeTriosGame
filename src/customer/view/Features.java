package customer.view;

/**
 * The features of a card or cell's event listeners when clicking on them.
 */
public interface Features {

  /**
   * Obtains the card index in hand the player (either human or ai) wants to play with.
   * Finds the matching card name for the index provided.
   *
   * @param cardIndex Index of the card in hand.
   */
  void selectCard(int cardIndex);

  /**
   * Select certain cell from game grid, given the cells' position.
   *
   * @param row The row num of the cell.
   * @param col The col num of the cell.
   */
  void selectCell(int row, int col);
}
