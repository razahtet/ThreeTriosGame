package battledecorator;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import customer.gamefeatures.BattleType;
import customer.gamefeatures.Card;
import customer.gamefeatures.ModType;
import customer.model.ThreeTriosGameModel;
import customer.model.ThreeTriosModel;

/**
 * Class that tests all the battle variant decorators to see if they are working correctly.
 */
public class TestBattleDecorator {
  ThreeTriosModel model = new ThreeTriosGameModel();
  Card[][] gameG;
  List<Card> playerAHand;
  List<Card> playerBHand;

  private void setUp(BattleType bt, ModType mt) {
    model = new ThreeTriosGameModel();
    model.startGame(
            "src\\customer\\ConstructorFiles\\GridFile_11",
            "src\\customer\\ConstructorFiles\\CardFile_5",
            false);
    gameG = model.getGameGrid().getGrid();
    for (Card[] cards : gameG) {
      for (Card card : cards) {
        card.setBattleType(bt);
        card.setModType(mt);
      }
    }
    playerAHand = model.getPlayerA().getHand();
    for (Card card : playerAHand) {
      card.setBattleType(bt);
      card.setModType(mt);
    }
    playerBHand = model.getPlayerB().getHand();
    for (Card card : playerBHand) {
      card.setBattleType(bt);
      card.setModType(mt);
    }
  }

  /**
   * Test a card battle when the battle mode is normal (compared by what number is greater in the
   * facing directions).
   */
  @Test
  public void testNormalBattleDecorator() {
    setUp(BattleType.NORMAL, ModType.NORMAL);
    Card card1 = model.getPlayerB().getHand().get(0);
    System.out.println(card1.getColor());
    model.playToGrid(0, 0, 0, model.getPlayerB());
    Card card2 = model.getPlayerA().getHand().get(0);
    System.out.println(card2.getColor());
    model.playToGrid(0, 1, 0, model.getPlayerA());
    model.battle(card2, 1, 0, model.getPlayerA().getColor());
    System.out.println(model.getGameGrid().getGrid()[0][0].toString());
    System.out.println(model.getGameGrid().getGrid()[1][0].toString());
    Assert.assertNotEquals("Checking battle success.",
            model.getGameGrid().getGrid()[0][0].getColor(),
            model.getGameGrid().getGrid()[1][0].getColor());
  }

  /**
   * Test a card battle when the battle mode is reverse (compared by what number is less in the
   * facing directions).
   */
  @Test
  public void testReverseBattleDecorator() {
    setUp(BattleType.REVERSE, ModType.NORMAL);
    Card card2 = playerAHand.get(0);
    System.out.println(card2.getColor());
    model.playToGrid(0, 1, 0, model.getPlayerA());
    Card card1 = playerBHand.get(0);
    System.out.println(card1.getColor());
    model.playToGrid(0, 0, 0, model.getPlayerB());
    model.battle(card2, 1, 0, model.getPlayerA().getColor());
    System.out.println(model.getGameGrid().getGrid()[0][0].toString());
    System.out.println(model.getGameGrid().getGrid()[1][0].toString());
    Assert.assertEquals("Checking battle success.",
            model.getGameGrid().getGrid()[0][0].getColor(),
            model.getGameGrid().getGrid()[1][0].getColor());
  }

  /**
   * Test a card battle when the battle mode is "fallen ace" (compared by what number is greater in
   * the facing directions but 1 is considered greater than ace).
   */
  @Test
  public void testFallenAceDecorator() {
    setUp(BattleType.ACE, ModType.NORMAL);
    Card card1 = playerBHand.get(4);
    System.out.println(card1.getColor());
    model.playToGrid(4, 0, 0, model.getPlayerB());
    Card card2 = playerAHand.get(4);
    System.out.println(card2.getColor());
    model.playToGrid(4, 1, 0, model.getPlayerA());
    model.battle(card2, 1, 0, model.getPlayerA().getColor());
    System.out.println(model.getGameGrid().getGrid()[0][0].toString());
    System.out.println(model.getGameGrid().getGrid()[1][0].toString());
    Assert.assertNotEquals("Checking battle success.",
            model.getGameGrid().getGrid()[0][0].getColor(),
            model.getGameGrid().getGrid()[1][0].getColor());
  }

  /**
   * Test 1 for testing a card battle when the battle mode is reverse and "fallen ace"
   * (compared by what number is less in the facing directions, and ace should win against 1).
   * In this test, the first card that got placed gets reversed.
   */
  @Test
  public void testReverseAndFallenAceDecorator1() {
    setUp(BattleType.REVERSEANDACE, ModType.NORMAL);
    Card card1 = playerBHand.get(4);
    System.out.println(card1.getColor());
    model.playToGrid(4, 0, 0, model.getPlayerB());
    Card card2 = playerAHand.get(4);
    System.out.println(card2.getColor());
    model.playToGrid(4, 1, 0, model.getPlayerA());
    model.battle(card2, 1, 0, model.getPlayerA().getColor());
    System.out.println(model.getGameGrid().getGrid()[0][0].toString());
    System.out.println(model.getGameGrid().getGrid()[1][0].toString());
    Assert.assertEquals("Checking battle success.",
            model.getGameGrid().getGrid()[0][0].getColor(),
            model.getGameGrid().getGrid()[1][0].getColor());
  }

  /**
   * Test 2 for testing a card battle when the battle mode is reverse and "fallen ace"
   * (compared by what number is less in the facing directions, and ace should win against 1).
   * Here, the first card that gets placed should not get reversed.
   */
  @Test
  public void testReverseAndAceBattleDecorator2() {
    setUp(BattleType.REVERSEANDACE, ModType.NORMAL);
    Card card2 = playerAHand.get(0);
    System.out.println(card2.getColor());
    model.playToGrid(0, 1, 0, model.getPlayerA());
    Card card1 = playerBHand.get(0);
    System.out.println(card1.getColor());
    model.playToGrid(0, 0, 0, model.getPlayerB());
    model.battle(card1, 1, 0, model.getPlayerA().getColor());
    System.out.println(model.getGameGrid().getGrid()[0][0].toString());
    System.out.println(model.getGameGrid().getGrid()[1][0].toString());
    Assert.assertNotEquals("Checking battle success.",
            model.getGameGrid().getGrid()[0][0].getColor(),
            model.getGameGrid().getGrid()[1][0].getColor());
  }
}
