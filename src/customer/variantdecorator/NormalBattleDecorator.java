package customer.variantdecorator;

/**
 * The normal battle mode, which compares if one number is greater than the other.
 */
public class NormalBattleDecorator implements IVariant {
  private final int numOne;
  private final int numTwo;

  /**
   * The constructor that takes in two numbers to compare them.
   *
   * @param numOne The first number to be compared with.
   * @param numTwo The second number to be compared with.
   */
  public NormalBattleDecorator(int numOne, int numTwo) {
    this.numOne = numOne;
    this.numTwo = numTwo;
  }

  /**
   * Returns true if the first number is greater than the second number, otherwise false.
   *
   * @return if the first number is greater than the second number, otherwise false.
   */
  @Override
  public boolean compareCard() {
    return numOne > numTwo;
  }
}
