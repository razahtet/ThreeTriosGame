package customer.variantdecorator;

/**
 * The extra modification to a battle mode where if the card attacking is as the defending card,
 * the card attacking wins.
 */
public class SameDecorator extends BaseVariantDecorator {
  private final int num1;
  private final int num2;

  /**
   * Constructor for a SameDecorator.
   *
   * @param wrapped The wrapped decorator that calls it super (could be the normal or reverse game
   *                mode).
   * @param num1    The first number to compare for this battle mode.
   * @param num2    The second number to compare for this battle mode.
   */
  public SameDecorator(IVariant wrapped, int num1, int num2) {
    super(wrapped);
    this.num1 = num1;
    this.num2 = num2;
  }

  /**
   * Returns true if num1 is equal to num2. Otherwise,
   * it just returns the battle mode before this modification (reverse, fallen ace,
   * reverse fallen ace, or normal).
   *
   * @return False or true depending on what num1 and num2 is.
   */
  @Override
  public boolean compareCard() {
    if (num1 == num2) {
      return true;
    } else {
      return super.compareCard();
    }
  }
}
