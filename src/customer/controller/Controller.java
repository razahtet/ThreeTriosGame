package customer.controller;

import customer.gamefeatures.IPlayer;

/**
 * Interface for game controllers, extending GameEventListener to handle game events.
 */
public interface Controller extends GameEventListener {

  /**
   * Set up the game.
   */
  void setupGame();

  /**
   * Execute player's turn.
   */
  void executeTurn();

  /**
   * End the turn for the current player.
   */
  void endTurn();

  /**
   * Check whether the player is human.
   *
   * @return Whether the player is human.
   */
  boolean isHumanControlled();

  /**
   * Update the game customer.model using the information from the customer.controller.
   *
   * @param player  The current player in turn.
   * @param cardIdx The card index in the player's hand.
   * @param row     The row position where the card is to be played.
   * @param col     The column position where the card is to be played.
   */
  void updateGameModel(IPlayer player, int cardIdx, int row, int col);
}
