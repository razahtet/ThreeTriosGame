package customer.variantdecorator;

/**
 * The base battle mode decorator that takes in a battle mode decorator to return its function call
 * so that multiple things can be done on top of each other (e.g. reverse and fallen ace).
 */
public abstract class BaseVariantDecorator implements IVariant {
  private final IVariant wrapped;

  /**
   * Constructor that calls the super to have battle modes combined.
   *
   * @param wrapped An instance of the super battle mode decorator.
   */
  public BaseVariantDecorator(IVariant wrapped) {
    this.wrapped = wrapped;
  }

  /**
   * Returns the wrapped's compareCard function's return value.
   *
   * @return The wrapped's compareCard function's return value.
   */
  public boolean compareCard() {
    return wrapped.compareCard();
  }
}
