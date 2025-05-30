package customer.gamefeatures;

/**
 * Stores the value of different directions on card.
 */
public class Direction {

  private final int attackVal;

  /**
   * Constructor for the Direction.
   *
   * @param attackVal The number at this direction.
   */
  public Direction(int attackVal) {
    // make sure the ATK value is between 1-10
    if (attackVal < 0 || attackVal > 10) {
      throw new IllegalArgumentException(
              "Card attack value should between 1 and 10.");
    }
    this.attackVal = attackVal;
  }

  /**
   * Constructor for Direction that copies another Direction.
   *
   * @param another Another Direction object.
   */
  public Direction(Direction another) {
    this.attackVal = another.attackVal;
  }

  /**
   * Getter method for the card number.
   *
   * @return The number of card under this direction.
   */
  public int getCardNum() {
    return this.attackVal;
  }

  /**
   * Get the direction in String format.
   *
   * @return Direction in String.
   */
  public String toString() {
    String attackVal = Integer.toString(this.attackVal);
    if (attackVal.equals("10")) {
      attackVal = "A";
    }
    return attackVal;
  }
}
