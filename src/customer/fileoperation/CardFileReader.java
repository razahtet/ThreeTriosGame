package customer.fileoperation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import customer.gamefeatures.Card;
import customer.gamefeatures.GameCard;

/**
 * File reader class to initialize hand cards using configuration file.
 */
public class CardFileReader {

  /**
   * Construct a List of GameCard using a txt file that contains the basic
   * constructing information.
   *
   * @param filePath  File path in String.
   * @param cardColor The color of the hand card.
   * @return A list of GameCard.
   */
  public List<Card> getHandCardUsingConfigFile(String filePath, String cardColor) {
    try {
      File configFile = new File(filePath);
      Scanner scanner = new Scanner(configFile);
      // an empty list of card going to be updated
      List<Card> handCard = new ArrayList<>();

      // check through the card txt file line by line
      while (scanner.hasNextLine()) {
        // separate the element in the string to a list using space
        String[] cardInfo = scanner.nextLine().split("\\s+");
        if (cardInfo.length != 5) {
          throw new IllegalArgumentException(
                  "The game card info should only contains 5 element.");
        }
        String cardName = cardInfo[0];
        try {
          int northATK = Integer.parseInt(cardInfo[1]);
          int southATK = Integer.parseInt(cardInfo[2]);
          int eastATK = Integer.parseInt(cardInfo[3]);
          int westATK = Integer.parseInt(cardInfo[4]);
          Card card = new GameCard(cardName, cardColor, northATK, southATK, eastATK, westATK);
          handCard.add(card);
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("The attack value should be an integer.");
        }

      }
      return handCard;
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found: " + filePath);
    }
  }
}
