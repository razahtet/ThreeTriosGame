package view;

import org.junit.Assert;
import org.junit.Test;

import customer.fileoperation.ConfigurationReader;
import customer.gamefeatures.Card;
import customer.gamefeatures.GameCard;
import customer.gamefeatures.IGameGrid;

/**
 * Test the customer.provider.view format of the game grid.
 */
public class GameGridViewTest {

  // the game grid with no holes inside
  IGameGrid gridOne = new ConfigurationReader().buildGridUsingConfigFile(
          "src\\customer\\ConstructorFiles\\GridFile_11");
  // the game grid with holes where all card cells can reach each other
  IGameGrid gridTwo = new ConfigurationReader().buildGridUsingConfigFile(
          "src\\customer\\ConstructorFiles\\GridFile_9");
  // the game grid with holes where at least two groups of card cells cannot reach each other.
  IGameGrid gridThree = new ConfigurationReader().buildGridUsingConfigFile(
          "src\\customer\\ConstructorFiles\\GridFile_10");

  /**
   * Check the string format of the first grid.
   */
  @Test
  public void testGridOne() {
    String expected = "___\n___\n___";
    Assert.assertEquals("Checking the string format of the first grid.",
            expected,
            gridOne.toString());
  }

  /**
   * Check the string format of the second grid.
   */
  @Test
  public void testGridTwo() {
    String expected =
            "_ __\n_ __\n____\n__ _\n____";
    Assert.assertEquals("Checking the string format of the second grid.",
            expected,
            gridTwo.toString());
  }

  /**
   * Check the string format of the third grid.
   */
  @Test
  public void testGridThree() {
    String expected =
            "___ __\n___ __\n__ ___";
    Assert.assertEquals("Checking the string format of the third grid.",
            expected,
            gridThree.toString());
  }

  /**
   * Check the string format of the third grid when a card has been played to grid.
   */
  @Test
  public void testPlayCardToGridThree() {
    Card card = new GameCard("WindBird", "BLUE", 4, 5, 7, 10);
    gridThree.playToGrid(card, 2, 0);
    String expected =
            "__B __\n___ __\n__ ___";
    Assert.assertEquals("Checking the string format of the third grid.",
            expected,
            gridThree.toString());
  }

  /**
   * Check the string format of the third grid when more than one card has been played to grid.
   */
  @Test
  public void testPlayToGridTwo() {
    Card card1 = new GameCard("WindBird", "RED",
            1, 2, 3, 4);
    Card card2 = new GameCard("CorruptKing", "BLUE",
            4, 3, 2, 1);
    Card card3 = new GameCard("AngryDragon", "BLUE",
            4, 5, 7, 10);
    gridTwo.playToGrid(card1, 0, 0);
    gridTwo.playToGrid(card2, 2, 0);
    gridTwo.playToGrid(card3, 1, 4);
    String expected =
            "R B_\n_ __\n____\n__ _\n_B__";
    Assert.assertEquals("Checking the string format of the second grid.",
            expected,
            gridTwo.toString());

  }
}
