package customer.gamefeatures;

import java.util.List;

/**
 * The interface of the game player.
 */
public interface IPlayer {
  /**
   * Get the color represent the player.
   *
   * @return The color represent the player.
   */
  String getColor();

  /**
   * Add new card to hand.
   *
   * @param card The card to be added to hand.
   */
  void addCardsToHand(Card card);

  /**
   * Get the card at the given index from hand.
   *
   * @param index The card index inside hand.
   * @return The GameCard in hand at the given index.
   */
  Card getCardFromHand(int index);

  /**
   * Get the hand card of the player.
   *
   * @return The hand card of the player.
   */
  List<Card> getHand();

  /**
   * Create a copy of the game player.
   *
   * @return The copy of the player.
   */
  IPlayer copy();
}
