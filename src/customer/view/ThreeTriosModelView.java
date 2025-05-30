package customer.view;

import customer.gamefeatures.Card;
import customer.gamefeatures.IPlayer;
import customer.model.ThreeTriosModel;

/**
 * The class which implements the GameView interface and handle methods inside.
 */
public class ThreeTriosModelView implements GameView {

  private final ThreeTriosModel model;

  /**
   * Constructor for the ThreeTriosModelView.
   *
   * @param model Game customer.provider.model.
   */
  ThreeTriosModelView(ThreeTriosModel model) {
    this.model = model;
  }

  @Override
  public String toString(IPlayer player) {
    String expected = "Player: " + player.getColor() + "\n";
    expected = expected + model.getGameGrid().toString() + "\n";
    expected = expected + "Hand:\n";
    // check through the hand of the player
    for (int i = 0; i < player.getHand().size(); i += 1) {
      Card card = player.getHand().get(i);
      // last hand card
      if (i == player.getHand().size() - 1) {
        expected = expected + card.toString();
      } else {
        expected = expected + card.toString() + "\n";
      }
    }
    return expected;
  }
}