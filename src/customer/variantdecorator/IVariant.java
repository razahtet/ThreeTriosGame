package customer.variantdecorator;

/**
 * Interface for the different battle modes.
 */
public interface IVariant {
  /**
   * Compares two values of the directions of two cards facing in the same direction, depending
   * on the battle mode (the decorator this function is inside).
   *
   * @return True or false depending on what the battle mode is meant to do.
   */
  boolean compareCard();
}
