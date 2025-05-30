package customer.model;

import java.util.List;

import customer.gamefeatures.Card;
import customer.gamefeatures.IGameGrid;
import customer.gamefeatures.IPlayer;

/**
 * The interface of the ThreeTrios game, contains methods to
 * output information instead of mutate the current model.
 */
public interface ReadonlyThreeTriosModel {

  /**
   * Check if the game is over.
   *
   * @return true if the game is over.
   */
  boolean isOver();

  /**
   * Get the winner of the game.
   *
   * @return the player who has won.
   */
  IPlayer getWinner();

  /**
   * Get Player A's information.
   *
   * @return player A.
   */
  IPlayer getPlayerA();

  /**
   * Get Player B's information.
   *
   * @return player B.
   */
  IPlayer getPlayerB();

  /**
   * Get the current game grid.
   *
   * @return the game grid.
   */
  IGameGrid getGameGrid();

  /**
   * Get the current player whose turn it is.
   *
   * @return the current player.
   */
  String getCurrentPlayer();

  /**
   * Get the number of cards that can be flipped given the parameters.
   *
   * @param cardIdx index of the card.
   * @param x       x coordinate of the grid.
   * @param y       y coordinate of the grid.
   * @param player  player attempting the move.
   * @return number of cards that can be flipped.
   */
  int getNumCardsCanFlip(int cardIdx, int x, int y, IPlayer player);

  /**
   * Get the score of a given player.
   *
   * @param player the player.
   * @return the score of the player.
   */
  int getAPlayerScore(IPlayer player);

  /**
   * Get a copy of the current game grid.
   *
   * @return a copy of the grid.
   */
  Card[][] getCopyOfGameGrid();

  /**
   * Returns all the original cards in the game when the game started.
   *
   * @return A list of the original cards in the game.
   */
  List<Card> returnAllCards();
}
