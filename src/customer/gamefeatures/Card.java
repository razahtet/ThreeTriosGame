package customer.gamefeatures;

/**
 * The interface represent game card.
 */
public interface Card {

  /**
   * Compare the card ATK value with another card.
   * Check whether this card wins.
   *
   * @param card      The card to be compared.
   * @param direction The direction of the ATK value that is going to be compared.
   * @return Whether this card is larger than the argument card.
   */
  boolean compare(Card card, String direction);

  /**
   * Get the number of card at certain direction.
   *
   * @param direction The direction of the ATK value we want.
   * @return Prefer ATK value.
   */
  int getNum(String direction);

  /**
   * Get the current color of the card.
   */
  String getColor();

  /**
   * Reverse the card color.
   */
  void reverseColor();

  /**
   * Return the game card information in the format of String.
   *
   * @return The card info in String.
   */
  String toString();

  /**
   * Return the card name in String.
   *
   * @return The card name.
   */
  String getName();

  /**
   * Get the percentage probability of the card to be flipped at certain direction.
   *
   * @param direction The direction want to be checked.
   * @return Probability of the card to be flipped.
   */
  int getChanceToFlip(String direction);

  /**
   * Create a copy of the Card.
   *
   * @return The copy of the card.
   */
  Card copy();

  /**
   * Sets the battle type of the card depending on the battle mode the game is in.
   *
   * @param battleType The battle mode.
   */
  void setBattleType(BattleType battleType);

  /**
   * Sets the modification type of the battle mode depending on what the added modification is.
   *
   * @param modType The modification add-on to the battle mode.
   */
  void setModType(ModType modType);
}
