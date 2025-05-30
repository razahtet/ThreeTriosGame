package gamefeatures;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import customer.fileoperation.CardFileReader;
import customer.gamefeatures.Card;
import customer.gamefeatures.GameCard;

/**
 * Tests about the game card.
 */
public class TestGameCard {

  Card card1 = new GameCard("WindBird", "RED",
          1, 2, 3, 4);
  Card card2 = new GameCard("CorruptKing", "BLUE",
          4, 3, 2, 1);

  /**
   * Test whether the game card can be constructed correctly.
   */
  @Test
  public void testCardConstruct() {
    Assert.assertTrue("Checking the construction of the card.",
            card1.getNum("north") == 1
                    &&
                    card2.getNum("east") == 2);

  }

  /**
   * Test the case when the given ATK value is negative.
   */
  @Test
  public void testNegativeATKVal() {
    try {
      Card invalidCard = new GameCard("WindBird", "RED",
              1, -2, 3, 4);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

  /**
   * Test the case when the given ATK value larger than 10.
   */
  @Test
  public void testLargerThanTenATKVal() {
    try {
      Card invalidCard = new GameCard("WindBird", "RED",
              1, 20, 3, 4);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

  /**
   * Test the method reverse color.
   */
  @Test
  public void testReverseColor() {
    card1.reverseColor();
    Assert.assertEquals("Checking the reverseColor method,", "BLUE", card1.getColor());
  }

  /**
   * Test the compare method.
   */
  @Test
  public void testCompareNormal() {
    boolean firstCompare = card1.compare(card2, "north"); // false
    boolean secondCompare = card1.compare(card2, "west"); // true
    Assert.assertTrue("Checking the compare method.",
            !firstCompare && secondCompare);
  }

  /**
   * Test compare when there equal ATK value appears.
   */
  @Test
  public void testCompareTie() {
    Card newCard = new GameCard("Name", "RED",
            1, 1, 1, 1);
    Assert.assertFalse("Checking the compare method.",
            card2.compare(newCard, "west"));
  }

  /**
   * Test compare method when the card color is the same.
   */
  @Test
  public void testCompareSameColor() {
    Card newCard = new GameCard("Name", "RED",
            1, 1, 1, 1);
    Assert.assertFalse("Checking the compare method.",
            card1.compare(newCard, "west"));
  }

  /**
   * Test the case when a wrong file path is given,
   * expect an IAE to be thrown.
   */
  @Test
  public void testWrongFilePath() {
    try {
      List<Card> hand = new CardFileReader().getHandCardUsingConfigFile(
              "C:\\Users\\W\\Desktop\\OOD",
              "RED");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

  /**
   * Test when one of the given ATK value is a decimal number,
   * expect an IAE to be thrown.
   */
  @Test
  public void testNonIntATKValue() {
    try {
      List<Card> hand = new CardFileReader().getHandCardUsingConfigFile(
              "src\\customer\\customer\\ConstructorFiles\\CardFile_2",
              "BLUE");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

  /**
   * Test when one of the card info does not have enough element,
   * expect an IAE to be thrown.
   */
  @Test
  public void testMissingElement() {
    try {
      List<Card> hand = new CardFileReader().getHandCardUsingConfigFile(
              "src\\customer\\ConstructorFiles\\CardFile_3",
              "BLUE");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

  /**
   * Construct an empty list of card.
   */
  @Test
  public void testEmptyHandCard() {
    List<Card> hand = new CardFileReader().getHandCardUsingConfigFile(
            "src\\customer\\ConstructorFiles\\CardFile_1",
            "RED");
    Assert.assertEquals(
            "Checking the construction of an empty list of card using txt file.",
            0,
            hand.size());
  }

  /**
   * Construct a list of hand card that can satisfy the game grid
   * created by GridFile_9.
   */
  @Test
  public void testNormalHandCardOne() {
    List<Card> hand = new CardFileReader().getHandCardUsingConfigFile(
            "src\\customer\\ConstructorFiles\\CardFile_4",
            "RED");
    Assert.assertEquals(
            "Checking if all card info in the given file " +
                    "has been correctly turn into GameCards in card list.",
            6,
            hand.size());
  }

  /**
   * Construct a list of hand card that can satisfy the game grid
   * created by GridFile_10.
   */
  @Test
  public void testNormalHandCardTwo() {
    List<Card> hand = new CardFileReader().getHandCardUsingConfigFile(
            "src\\customer\\ConstructorFiles\\CardFile_5",
            "RED");
    Assert.assertEquals(
            "Checking if all card info in the given file " +
                    "has been correctly turn into GameCards in card list.",
            9,
            hand.size());
  }

}
