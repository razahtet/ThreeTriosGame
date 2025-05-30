package strategy;

import org.junit.Assert;
import org.junit.Test;

import java.awt.Point;

import customer.gamefeatures.Card;
import customer.model.ThreeTriosGameModel;
import customer.model.ThreeTriosModel;
import customer.strategiccomputerplayer.CornerStrategy;
import customer.strategiccomputerplayer.Move;
import customer.strategiccomputerplayer.Strategy;

/**
 * Test class for the corner provider.strategies,
 * test AI place card on grid base on the corner first strategy.
 */
public class TestCornerStrategy {

  /**
   * Test the case when the AI want to put card on the tight bottom corner.
   */
  @Test
  public void testPlaceCardOnRightBotCorner() {
    ThreeTriosModel model = new ThreeTriosGameModel();
    model.startGame("src\\customer\\ConstructorFiles\\GridFile_11",
            "src\\customer\\ConstructorFiles\\CardFile_5",
            false);
    Strategy cornerStrategy = new CornerStrategy(model);
    Move move = cornerStrategy.getBestMove(model.getPlayerA());
    Assert.assertEquals("Check play card on the bottom right corner.",
            new Point(2, 2),
            move.getPosition());
  }

  /**
   * Test the case to place the card in the top left corner.
   */
  @Test
  public void testPlaceCardOnLeftTopCorner() {
    ThreeTriosModel model = new ThreeTriosGameModel();
    model.startGame("src\\customer\\ConstructorFiles\\GridFile_11",
            "src\\customer\\ConstructorFiles\\CardFile_5",
            false);
    model.playToGrid(0, 2, 0, model.getPlayerA());
    model.playToGrid(0, 0, 2, model.getPlayerB());
    Strategy cornerStrategy = new CornerStrategy(model);
    Move move = cornerStrategy.getBestMove(model.getPlayerB());
    Assert.assertEquals("Check play card on the top left corner.",
            new Point(0, 0),
            move.getPosition());
  }

  /**
   * Test the case when only one card left, computer will put card to there.
   */
  @Test
  public void testOtherCornerIsFullOfCard() {
    ThreeTriosModel model = new ThreeTriosGameModel();
    model.startGame("src\\customer\\ConstructorFiles\\GridFile_11",
            "src\\customer\\ConstructorFiles\\CardFile_5",
            false);
    Card cardA = model.getPlayerA().getHand().get(0);
    Card cardB = model.getPlayerB().getHand().get(0);
    model.playToGrid(0, 2, 0, model.getPlayerA());
    model.battle(cardA, 2, 0, model.getPlayerA().getColor());
    model.playToGrid(0, 0, 2, model.getPlayerB());
    model.battle(cardB, 0, 2, model.getPlayerB().getColor());
    model.playToGrid(0, 2, 2, model.getPlayerA());

    Strategy cornerStrategy = new CornerStrategy(model);
    Move move = cornerStrategy.getBestMove(model.getPlayerB());
    Assert.assertEquals("Check play card on the top left corner.",
            new Point(0, 0),
            move.getPosition());
  }

  /**
   * Test the case when all the corner is full of card, then the card could only
   * be placed in the middle of the grid.
   */
  @Test
  public void testEveryCornerIsFull() {
    ThreeTriosModel model = new ThreeTriosGameModel();
    model.startGame("src\\customer\\ConstructorFiles\\GridFile_11",
            "src\\customer\\ConstructorFiles\\CardFile_5",
            false);
    Card cardA = model.getPlayerA().getHand().get(0);
    Card cardB = model.getPlayerB().getHand().get(0);
    Card cardC = model.getPlayerA().getHand().get(1);
    Card cardD = model.getPlayerB().getHand().get(1);
    model.playToGrid(0, 2, 0, model.getPlayerA());
    model.battle(cardA, 2, 0, model.getPlayerA().getColor());
    model.playToGrid(0, 0, 2, model.getPlayerB());
    model.battle(cardB, 0, 2, model.getPlayerB().getColor());
    model.playToGrid(0, 2, 2, model.getPlayerA());
    model.battle(cardC, 2, 2, model.getPlayerA().getColor());
    model.playToGrid(0, 0, 0, model.getPlayerB());
    model.battle(cardD, 0, 0, model.getPlayerB().getColor());

    Strategy cornerStrategy = new CornerStrategy(model);
    Move move = cornerStrategy.getBestMove(model.getPlayerA());
    Assert.assertEquals("Check play card on the middle of the grid.",
            new Point(2, 1),
            move.getPosition());
  }

}
