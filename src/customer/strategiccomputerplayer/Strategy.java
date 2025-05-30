package customer.strategiccomputerplayer;

import customer.gamefeatures.Card;
import customer.gamefeatures.IPlayer;

/**
 * The interface to handle every AI strategy that the computer could take.
 */
public interface Strategy {

  /**
   * Check whether the given position is at the corner.
   *
   * @param x The x coordinate(col num) of the card.
   * @param y The y coordinate(row num) of the card.
   * @return Whether the given position is at corner.
   */
  boolean isCorner(int x, int y);

  /**
   * Check whether card could be played to the given position.
   *
   * @param x The x coordinate(col num) of the position.
   * @param y The y coordinate(row num) of the position.
   * @return Whether the position is an empty cell.
   */
  boolean ableToPlay(int x, int y);

  /**
   * Get the best move for the given player.
   *
   * @param player The player for this round.
   * @return The best move for this player.
   */
  Move getBestMove(IPlayer player);

  /**
   * Get the percentage chance for the card be flipped.
   *
   * @param card The card be played in this round.
   * @param x    The column number of the card.
   * @param y    The row number of the card.
   * @return The probability of the card be flipped in later turn.
   */
  int getBeFlippedProbability(Card card, int x, int y);
}
