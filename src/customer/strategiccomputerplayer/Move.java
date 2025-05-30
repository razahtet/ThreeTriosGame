package customer.strategiccomputerplayer;

import java.awt.Point;

/**
 * Represents a potential move in the ThreeTrios game.
 * Stores the potential card's index in hand,
 * the point on the grid where it's going to be played,
 * and the potential score it could earn.
 */
public class Move implements Comparable<Move> {

  private final Point position;
  private final int cardIdx;
  private final int score;
  private final int probabilityToFlip;

  /**
   * Default constructor for the Move class.
   */
  public Move() {
    this.position = new Point(-1, -1);
    this.cardIdx = -1;
    this.score = -1;
    this.probabilityToFlip = 0;
  }

  /**
   * Parameterized constructor for the Move class.
   *
   * @param position          The position on the grid to play the card.
   * @param cardIdx           The index of the card in the player's hand.
   * @param score             The score earned by this move.
   * @param probabilityToFlip The probability of the card being flipped.
   */
  public Move(Point position, int cardIdx, int score, int probabilityToFlip) {
    this.position = position;
    this.cardIdx = cardIdx;
    this.score = score;
    this.probabilityToFlip = probabilityToFlip;
  }

  /**
   * Gets the score earned in this move.
   *
   * @return The score earned.
   */
  public int getScore() {
    return score;
  }

  /**
   * Gets the card index in this move.
   *
   * @return The card index.
   */
  public int getCardIdx() {
    return cardIdx;
  }

  /**
   * Gets the position where the card is played.
   *
   * @return The position as a Point.
   */
  public Point getPosition() {
    return position;
  }

  @Override
  public int compareTo(Move other) {
    // Compare scores in descending order
    if (this.score != other.score) {
      return Integer.compare(other.score, this.score);
    }
    // Compare flip probabilities in ascending order
    if (this.probabilityToFlip != other.probabilityToFlip) {
      return Integer.compare(this.probabilityToFlip, other.probabilityToFlip);
    }
    // Compare positions: leftmost (lower x), then topmost (lower y)
    if (!this.position.equals(other.position)) {
      if (this.position.x != other.position.x) {
        return Integer.compare(this.position.x, other.position.x);
      } else {
        return Integer.compare(this.position.y, other.position.y);
      }
    }
    // Compare card indices in ascending order
    return Integer.compare(this.cardIdx, other.cardIdx);
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Move)) {
      return false;
    }
    Move other = (Move) obj;
    return this.score == other.score &&
            this.probabilityToFlip == other.probabilityToFlip &&
            this.cardIdx == other.cardIdx &&
            this.position.equals(other.position);
  }

  @Override
  public int hashCode() {
    int result = position.hashCode();
    result = 31 * result + cardIdx;
    result = 31 * result + score;
    result = 31 * result + probabilityToFlip;
    return result;
  }
}
