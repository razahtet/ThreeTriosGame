package strategy;

import org.junit.Assert;
import org.junit.Test;

import java.awt.Point;

import customer.gamefeatures.Card;
import customer.model.ThreeTriosGameModel;
import customer.model.ThreeTriosModel;
import customer.strategiccomputerplayer.MaxFlipStrategy;
import customer.strategiccomputerplayer.Move;
import customer.strategiccomputerplayer.Strategy;

/**
 * Test class for the AI based on the MaxFlipStrategy.
 */
public class TestMaxFlipStrategy {

  /**
   * Test the normal case to place card on grid to flip as much card as it can.
   */
  @Test
  public void testNormalMaxFlipStrategy() {
    ThreeTriosModel model = new ThreeTriosGameModel();
    model.startGame("src\\customer\\ConstructorFiles\\GridFile_11",
            "src\\customer\\ConstructorFiles\\CardFile_5",
            false);
    Card cardA = model.getPlayerA().getHand().get(0);
    Card cardB = model.getPlayerB().getHand().get(1);
    model.playToGrid(0, 0, 0, model.getPlayerA());
    model.battle(cardA, 0, 0, model.getPlayerA().getColor());
    // the x = 1, y = 2 here will be reversed to x = 2, y = 1 when they actually be placed on grid
    // therefore, it's different with the Point in the Assert test below
    model.playToGrid(1, 1, 2, model.getPlayerB());
    model.battle(cardB, 1, 2, model.getPlayerB().getColor());
    Strategy maxFlip = new MaxFlipStrategy(model);
    Move move = maxFlip.getBestMove(model.getPlayerA());
    Assert.assertEquals("Check play card on the top left corner.",
            new Point(1, 2),
            move.getPosition());
  }
}
