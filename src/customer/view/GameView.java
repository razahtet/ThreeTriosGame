package customer.view;

import customer.gamefeatures.IPlayer;

/**
 * The interface for outputting the game customer.provider.view.
 */
public interface GameView {

  /**
   * Return the game customer.provider.model in the form of String.
   *
   * @param player Player in this turn.
   * @return Game customer.provider.model in String.
   */
  String toString(IPlayer player);
}
