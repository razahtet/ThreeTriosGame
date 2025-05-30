package customer.model;

import customer.gamefeatures.Card;
import customer.gamefeatures.IPlayer;

/**
 * Interface of the Three Trios game customer.provider.model, include the core logic and data
 * handle, have two provider.players, a game grid that the player can play on.
 * When all the card cells in the grid are full, check which color have the most card in the gird.
 */
public interface ThreeTriosModel extends ReadonlyThreeTriosModel {

  /**
   * Method to initialize and start the game, using the given file path to construct
   * the initial state of this game grid and the hand card list for two provider.players.
   *
   * @param gridFilePath Path to the txt file contains the GameGrid information.
   * @param cardFilePath Path to the txt file contains the hand card information for all cards.
   * @param shuffle      Whether the game wants to shuffle card.
   */
  void startGame(String gridFilePath, String cardFilePath, boolean shuffle);

  /**
   * Play the card to grid. Check whether the given player has already played a
   * card in this round, if so, throw an IllegalStateException.
   *
   * @param cardIdx The index of the card in the player's hand.
   * @param x       The col posn of the card is going to be played to grid.
   * @param y       The row posn of the card is going to be played to grid.
   * @param player  The player wants to play card.
   */
  void playToGrid(int cardIdx, int x, int y, IPlayer player);

  /**
   * After playing a card to game grid, start the battle of this round.
   * From the east side of the deck that was played,
   * check if the card adjacent to it is of a different color and smaller than the current card.
   * If so, flip the adjacent card, make it the same color as this card and keep checking all the
   * adjacent card of the card be flipped until no more cards could be flipped.
   *
   * @param card      The card be played to grid in this round.
   * @param x         The col coordinate of the card be played in this round.
   * @param y         The row coordinate of the card be played in this round.
   * @param cardColor The color of the card be played in this round.
   */
  void battle(Card card, int x, int y, String cardColor);

  /**
   * Get the number of certain color of cards on game board.
   *
   * @param color The color of cards that need to be calculated.
   * @return The number of card on board.
   */
  int countCardOnGrid(String color);

  /**
   * Create a copy of the current game mode.
   *
   * @return The copy of current game board.
   */
  ThreeTriosModel copyGameModel();

  void setCurrentPlayerMoved(boolean moved);

  boolean hasCurrentPlayerMoved();

  void switchToNextPlayer();
}
