package customer.controller;

/**
 * Interface to handle the event happening in the game.
 */
public interface GameEventListener {

  /**
   * Handle the event of a cell on grid being clicked.
   *
   * @param row The row of the cell in grid.
   * @param col The col of the cell in grid.
   */
  void onCellClicked(int row, int col);

  /**
   * Handle the event of a card in hand being selected.
   *
   * @param cardIndex The card index in hand.
   * @param cardColor The color of the card being selected.
   */
  boolean onCardSelected(int cardIndex, String cardColor);

  /**
   * Handles the hint showing during a player's turn when selecting a card, where the player
   * can see how many cards a player can flip playing a certain card to a certain cell in the grid.
   *
   * @param keyPressed A boolean that is true if hints should turn on or turn off or not,
   *                   if the key was pressed.
   * @return The card index of the card selected if a card is selected, otherwise, return -1.
   */
  int hintTime(boolean keyPressed, boolean noCardSelected);
}
