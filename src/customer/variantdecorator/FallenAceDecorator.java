package customer.variantdecorator;

/**
 * The Fallen Ace battle mode, where if the card attacking is a 1 and the defending is an A or 10,
 * the card attacking wins.
 */
public class FallenAceDecorator extends BaseVariantDecorator {
  private final int num1;
  private final int num2;

  /**
   * Constructor for a FallenAceDecorator.
   *
   * @param wrapped The wrapped decorator that calls it super (could be the normal or reverse game
   *                mode).
   * @param num1    The first number to compare for this battle mode.
   * @param num2    The second number to compare for this battle mode.
   */
  public FallenAceDecorator(IVariant wrapped, int num1, int num2) {
    super(wrapped);
    this.num1 = num1;
    this.num2 = num2;
  }

  /**
   * Returns false if num1 is 10 and num2 is 1. Returns true if they are reversed. Otherwise,
   * it just returns the battle mode before this modification (reverse or normal).
   *
   * @return False or true depending on what num1 and num2 is.
   */
  @Override
  public boolean compareCard() {
    if (num1 == 10 && num2 == 1) {
      return false;
    } else if (num1 == 1 && num2 == 10) {
      return true;
    } else {
      return super.compareCard();
    }
  }
}
