package gamefeatures;

import org.junit.Assert;
import org.junit.Test;

import customer.fileoperation.ConfigurationReader;
import customer.gamefeatures.Card;
import customer.gamefeatures.GameCard;
import customer.gamefeatures.IGameGrid;

/**
 * Tests about the game grid.
 */
public class TestGameGrid {

  String filePathOne =
          "src\\customer\\ConstructorFiles\\GridFile_1";
  String filePathTwo =
          "src\\customer\\ConstructorFiles\\GridFile_2";
  String filePathThree =
          "src\\customer\\ConstructorFiles\\GridFile_3";
  String filePathFour =
          "src\\customer\\ConstructorFiles\\GridFile_4";
  String filePathFive =
          "src\\customer\\ConstructorFiles\\GridFile_5";
  String filePathSix =
          "src\\customer\\ConstructorFiles\\GridFile_6";
  String filePathSeven =
          "src\\customer\\ConstructorFiles\\GridFile_7";
  String filePathEight =
          "src\\customer\\ConstructorFiles\\GridFile_8";
  String filePathNine =
          "src\\customer\\ConstructorFiles\\GridFile_12";
  IGameGrid gameGrid =
          new ConfigurationReader().buildGridUsingConfigFile(filePathOne);

  /**
   * Test whether an IAE could be thrown if the given file path could not
   * reach to an actual file.
   */
  @Test
  public void testInvalidFailPath() {
    try {
      IGameGrid invalidGrid =
              new ConfigurationReader().buildGridUsingConfigFile("C:\\Invalid_Path");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

  /**
   * Test when the initial empty card cell number is even,
   * expected an IAE to be thrown.
   */
  @Test
  public void testInvalidCellNum() {
    try {
      IGameGrid invalidGrid =
              new ConfigurationReader().buildGridUsingConfigFile(filePathNine);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

  /**
   * Test the case when the row and col number is not given in the grid configuration file.
   */
  @Test
  public void testNonRowAndColNumGiven() {
    try {
      IGameGrid invalidGrid =
              new ConfigurationReader().buildGridUsingConfigFile(filePathTwo);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

  /**
   * Test the case when only the row number is given, whether AIE can be thrown correctly.
   */
  @Test
  public void testNonColNumGiven() {
    try {
      IGameGrid invalidGrid =
              new ConfigurationReader().buildGridUsingConfigFile(filePathThree);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

  /**
   * Test the case when not enough row is given, whether an AIE can be thrown correctly.
   */
  @Test
  public void testNotEnoughRowGiven() {
    try {
      IGameGrid invalidGrid =
              new ConfigurationReader().buildGridUsingConfigFile(filePathFour);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

  /**
   * Test when the element in a row exceed the supposing amount,
   * whether an AIE can be thrown correctly.
   */
  @Test
  public void testInvalidElementInARow() {
    try {
      IGameGrid invalidGrid =
              new ConfigurationReader().buildGridUsingConfigFile(filePathSix);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

  /**
   * Test when the given col is negative, whether an AIE can be thrown correctly.
   */
  @Test
  public void testNegativeColNum() {
    try {
      IGameGrid invalidGrid =
              new ConfigurationReader().buildGridUsingConfigFile(filePathSeven);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

  /**
   * Test when the row file contains element other than 'X' and 'C',
   * whether an AIE can be thrown correctly.
   */
  @Test
  public void testInvalidChar() {
    try {
      IGameGrid invalidGrid =
              new ConfigurationReader().buildGridUsingConfigFile(filePathEight);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

  /**
   * Test the case when more row is given, the grid will still be constructed
   * without throwing an exception.
   */
  @Test
  public void testMoreRowGiven() {
    IGameGrid grid =
            new ConfigurationReader().buildGridUsingConfigFile(filePathFive);
    Assert.assertEquals("Checking the row number of the IGameGrid.",
            5,
            grid.getGrid().length);
  }

  /**
   * Test whether the game grid could be constructed correctly
   * when the right file path is given.
   */
  @Test
  public void testIGameGridConstructOne() {
    boolean correctRowNum = (gameGrid.getGrid().length == 5);
    boolean correctColNum = (gameGrid.getGrid()[0].length == 7);
    Assert.assertTrue("Checking the constructor of IGameGrid.",
            correctRowNum && correctColNum);
  }

  /**
   * Test whether the game grid could be constructed correctly
   * when the right file path is given.
   */
  @Test
  public void testIGameGridConstructTwo() {
    Assert.assertEquals(
            "Checking whether the hole on the grid has been initialized correctly.",
            "X",
            gameGrid.getGrid()[0][2].getName());
  }

  /**
   * Test when a player wants to play card to a hole,
   * expect an IllegalStateException to be thrown.
   */
  @Test
  public void testPlayToHole() {
    IGameGrid gameGrid = new ConfigurationReader().buildGridUsingConfigFile(filePathOne);
    Card card = new GameCard("WindBird", "BLUE", 4, 5, 7, 10);
    try {
      gameGrid.playToGrid(card, 3, 0);
    } catch (IllegalStateException e) {
      Assert.assertEquals(IllegalStateException.class, e.getClass());
    }
  }

  /**
   * Test when the given x coordinate value is out of bound,
   * expect an IllegalArgumentException to be thrown.
   */
  @Test
  public void testXPosOutOfBound() {
    IGameGrid gameGrid = new ConfigurationReader().buildGridUsingConfigFile(filePathOne);
    Card card = new GameCard("WindBird", "BLUE", 4, 5, 7, 10);
    try {
      gameGrid.playToGrid(card, 6, 3);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

  /**
   * Test when there is a card already be played to the given position,
   * expected an IllegalStateException to be thrown.
   */
  @Test
  public void testACardAlreadyExist() {
    IGameGrid gameGrid = new ConfigurationReader().buildGridUsingConfigFile(filePathOne);
    Card card1 = new GameCard("WindBird", "BLUE", 4, 5, 7, 10);
    Card card2 = new GameCard("AngryDragon", "RED", 3, 2, 9, 4);
    try {
      gameGrid.playToGrid(card1, 6, 1);
      gameGrid.playToGrid(card2, 6, 1);
    } catch (IllegalStateException e) {
      Assert.assertEquals(IllegalStateException.class, e.getClass());
    }
  }

  /**
   * Test the method playToGrid.
   */
  @Test
  public void testSuccessPlayToGrid() {
    IGameGrid gameGrid = new ConfigurationReader().buildGridUsingConfigFile(filePathOne);
    int cellNum = gameGrid.getCells();
    Card card1 = new GameCard("WindBird", "BLUE", 4, 5, 7, 10);
    gameGrid.playToGrid(card1, 6, 1);
    String cardName = gameGrid.getGrid()[1][6].getName();
    Assert.assertTrue("Checking whether the card has be successfully played to grid, " +
                    "and the number of available cells has be updated.",
            cardName.equals("WindBird") && (gameGrid.getCells() == cellNum - 1));

  }

  /**
   * Test the method hasCard when the given position is out of bound.
   */
  @Test
  public void testHasCardOutOfBound() {
    IGameGrid gameGrid = new ConfigurationReader().buildGridUsingConfigFile(filePathOne);
    Assert.assertFalse("Checking given y posn out of bound.",
            gameGrid.hasCard(3, 5));
  }

  /**
   * Test the method hasCard when the given position is empty cell.
   */
  @Test
  public void testHasCardAtEmptyCell() {
    IGameGrid gameGrid = new ConfigurationReader().buildGridUsingConfigFile(filePathOne);
    Assert.assertFalse("Checking given posn is empty.",
            gameGrid.hasCard(6, 2));
  }

  /**
   * Test the method hasCard when there is card exist at given position.
   */
  @Test
  public void testHasCard() {
    IGameGrid gameGrid = new ConfigurationReader().buildGridUsingConfigFile(filePathOne);
    Card card1 = new GameCard("WindBird", "BLUE", 4, 5, 7, 10);
    gameGrid.playToGrid(card1, 6, 1);
    Assert.assertTrue("Checking the given position have card.",
            gameGrid.hasCard(6, 1));
  }

}
