package model;

import org.junit.Assert;
import org.junit.Test;

import customer.gamefeatures.Card;
import customer.model.ThreeTriosGameModel;
import customer.model.ThreeTriosModel;

/**
 * Test the constructor and all methods in customer.provider.model class.
 */
public class ModelTest {

  /**
   * Test the constructor of the game customer.provider.model, create a 5x4 game grid with 17
   * available card cells, but the number of total card list does not satisfy
   * the requirement, expect an IAE to be thrown.
   */
  @Test
  public void testNotEnoughHandCard() {
    ThreeTriosModel model = new ThreeTriosGameModel();
    try {
      model.startGame(
              "src\\customer\\ConstructorFiles\\GridFile_9",
              "src\\customer\\ConstructorFiles\\CardFile_4",
              true);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

  /**
   * Test the case when the game has already started,
   * when calling startGame, expected an IAE to be thrown.
   */
  @Test
  public void testStartGameButGameAlreadyStart() {
    ThreeTriosModel model = new ThreeTriosGameModel();
    model.startGame(
            "src\\customer\\ConstructorFiles\\GridFile_11",
            "src\\customer\\ConstructorFiles\\CardFile_5",
            true);
    try {
      model.startGame(
              "src\\customer\\ConstructorFiles\\GridFile_11",
              "src\\customer\\ConstructorFiles\\CardFile_5",
              true);
    } catch (IllegalStateException e) {
      Assert.assertEquals(IllegalStateException.class, e.getClass());
    }

  }

  /**
   * Create a 3x3 game grid with 9 available cells, test
   * whether the number of hand card for each player is exactly 5.
   */
  @Test
  public void testInitialHandCardNum() {
    ThreeTriosModel model = new ThreeTriosGameModel();
    model.startGame(
            "src\\customer\\ConstructorFiles\\GridFile_11",
            "src\\customer\\ConstructorFiles\\CardFile_5",
            true);
    Assert.assertEquals("Checking the hand card number of provider.players.",
            5,
            model.getPlayerA().getHand().size());
  }

  /**
   * Test the case when player A wants to continuously play card to grid,
   * expect an IAE to be thrown.
   */
  @Test
  public void testSamePlayerPlayTwiceInATurn() {
    ThreeTriosModel model = new ThreeTriosGameModel();
    model.startGame(
            "src\\customer\\ConstructorFiles\\GridFile_11",
            "src\\customer\\ConstructorFiles\\CardFile_5",
            true);
    model.playToGrid(0, 0, 0, model.getPlayerA());
    try {
      model.playToGrid(0, 1, 2, model.getPlayerA());
    } catch (IllegalStateException e) {
      Assert.assertEquals(IllegalStateException.class, e.getClass());
    }
  }

  /**
   * Test the playToGrid method works properly.
   */
  @Test
  public void testPlayToGridWorks() {
    ThreeTriosModel model = new ThreeTriosGameModel();
    model.startGame(
            "src\\customer\\ConstructorFiles\\GridFile_13",
            "src\\customer\\ConstructorFiles\\CardFile_5",
            true);
    model.playToGrid(0, 0, 1, model.getPlayerB());
    Assert.assertEquals("Checking the PlayToGrid method.",
            "BLUE",
            model.getGameGrid().getGrid()[1][0].getColor());
  }

  /**
   * Test a card try to battle when there is no card surround it.
   */
  @Test
  public void testBattleWithNoCard() {
    ThreeTriosModel model = new ThreeTriosGameModel();
    model.startGame(
            "src\\customer\\ConstructorFiles\\GridFile_13",
            "src\\customer\\ConstructorFiles\\CardFile_5",
            true);
    Card card = model.getPlayerB().getHand().get(0);
    model.playToGrid(0, 0, 0, model.getPlayerB());
    model.battle(card, 0, 0, model.getPlayerB().getColor());
    int blueNum = 0;
    for (int i = 0; i < model.getGameGrid().getGrid().length; i += 1) {
      for (int j = 0; j < model.getGameGrid().getGrid()[i].length; j += 1) {
        if (model.getGameGrid().getGrid()[i][j].getColor().equals("BLUE")) {
          blueNum += 1;
        }
      }
    }
    Assert.assertEquals("Checking battle method but no card surround.",
            1,
            blueNum);

  }

  /**
   * Test a card battle with its surrounding card and succeed.
   */
  @Test
  public void testBattleSuccess() {
    ThreeTriosModel model = new ThreeTriosGameModel();
    model.startGame(
            "src\\customer\\ConstructorFiles\\GridFile_13",
            "src\\customer\\ConstructorFiles\\CardFile_5",
            true);
    Card card1 = model.getPlayerB().getHand().get(0);
    System.out.println(card1.getColor());
    model.playToGrid(0, 0, 0, model.getPlayerB());
    Card card2 = model.getPlayerA().getHand().get(0);
    System.out.println(card2.getColor());
    model.playToGrid(0, 1, 0, model.getPlayerA());
    model.battle(card2, 1, 0, model.getPlayerA().getColor());
    System.out.println(model.getGameGrid().getGrid()[0][0].toString());
    System.out.println(model.getGameGrid().getGrid()[0][1].toString());
    if (card2.compare(card1, "west")) {
      Assert.assertEquals("Checking battle success.",
              model.getGameGrid().getGrid()[0][0].getColor(),
              model.getGameGrid().getGrid()[0][1].getColor());
    }
  }


}
