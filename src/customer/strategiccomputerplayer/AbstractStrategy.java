package customer.strategiccomputerplayer;

import customer.gamefeatures.Card;
import customer.model.ThreeTriosModel;

/**
 * Abstract class for Strategy that layout the foundations of different Strategy classes that
 * can be made.
 */
public abstract class AbstractStrategy implements Strategy {

  protected ThreeTriosModel model;

  /**
   * Constructor for AbstractStrategy.
   *
   * @param model The current game customer.provider.model.
   */
  public AbstractStrategy(ThreeTriosModel model) {
    this.model = model;
  }

  @Override
  public boolean isCorner(int x, int y) {
    // Get the total row and column size of the game grid
    int maxRow = this.model.getGameGrid().getRow() - 1;
    int maxCol = this.model.getGameGrid().getCol() - 1;
    // Top-left corner
    if (y == 0 && x == 0) {
      return true;
    }
    // Top-right corner
    if (y == 0 && x == maxCol) {
      return true;
    }
    // Bottom-left corner
    if (y == maxRow && x == 0) {
      return true;
    }
    // Bottom-right corner
    return y == maxRow && x == maxCol;
  }

  @Override
  public boolean ableToPlay(int x, int y) {
    String name = this.model.getGameGrid().getGrid()[y][x].getName();
    return name.equals("C");
  }

  @Override
  public int getBeFlippedProbability(Card card, int x, int y) {
    // Get the total row and column size of the game grid
    int maxRow = this.model.getGameGrid().getRow() - 1;
    int maxCol = this.model.getGameGrid().getCol() - 1;
    int probability = 0;
    if (this.isCorner(x, y)) {
      // Top-left corner
      if (y == 0 && x == 0) {
        probability += card.getChanceToFlip("east");
        probability += card.getChanceToFlip("south");
      }
      // Top-right corner
      if (y == 0 && x == maxCol) {
        probability += card.getChanceToFlip("west");
        probability += card.getChanceToFlip("south");
      }
      // Bottom-left corner
      if (y == maxRow && x == 0) {
        probability += card.getChanceToFlip("east");
        probability += card.getChanceToFlip("north");
      }
      // Bottom-right corner
      if (y == maxRow && x == maxCol) {
        probability += card.getChanceToFlip("west");
        probability += card.getChanceToFlip("north");
      }
    } else {
      probability += card.getChanceToFlip("north");
      probability += card.getChanceToFlip("south");
      probability += card.getChanceToFlip("east");
      probability += card.getChanceToFlip("west");
    }
    return probability;
  }
}
