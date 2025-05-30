package customer.variantdecorator;

/**
 * The reverse battle mode, which is the reverse of the normal battle mode, where all the numbers
 * compared to be originally greater are changed to be less than.
 */
public class ReverseBattleDecorator extends BaseVariantDecorator {

  /**
   * Constructor for the Reverse Battle mode.
   *
   * @param wrapped The wrapped decorator that takes in the super (the normal game mode).
   */
  public ReverseBattleDecorator(IVariant wrapped) {
    super(wrapped);
  }

  /**
   * Returns the opposite or reverse of the super's compare card function return value. For example,
   * if the super was the normal game mode decorator, it would ideally return if the attacking
   * number is less than the number being compared to it, instead of if it was greater than it.
   *
   * @return The opposite of the super's compare card function return value.
   */
  @Override
  public boolean compareCard() {
    return !super.compareCard();
  }
}
