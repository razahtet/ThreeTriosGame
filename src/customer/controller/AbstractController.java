package customer.controller;

import customer.gamefeatures.Card;
import customer.gamefeatures.IPlayer;
import customer.model.ThreeTriosModel;

/**
 * An abstract class that implements the Controller interface,
 * containing a method to update the current game customer.provider.view.
 */
public abstract class AbstractController implements Controller {

  protected ThreeTriosModel model;

  public AbstractController(ThreeTriosModel model) {
    this.model = model;
  }

  @Override
  public void updateGameModel(IPlayer player, int cardIdx, int row, int col) {
    Card card = player.getHand().get(cardIdx);
    model.playToGrid(cardIdx, row, col, player);
    model.battle(card, row, col, player.getColor());
  }

}
