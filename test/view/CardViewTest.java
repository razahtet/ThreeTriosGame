package view;

import org.junit.Assert;
import org.junit.Test;

import customer.gamefeatures.Card;
import customer.gamefeatures.GameCard;

/**
 * Test the format of String represent the card information.
 */
public class CardViewTest {

  Card card1 = new GameCard("WindBird", "RED",
          1, 2, 3, 4);
  Card card2 = new GameCard("CorruptKing", "BLUE", 10, 2, 4, 10);

  /**
   * Test the toString method in GameCard.
   */
  @Test
  public void testToString() {
    Assert.assertEquals("Checking the toString method of the GameCard.",
            "WindBird 1 2 3 4",
            card1.toString());
  }

  /**
   * Test the toString method in GameCard when the attackVal is 10.
   */
  @Test
  public void testAnotherToString() {
    Assert.assertEquals("Checking the toString method of the GameCard.",
            "CorruptKing A 2 4 A",
            card2.toString());
  }
}
