package customer.view;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * Graph class that constructs the graph customer.provider.view of a single game card.
 */
public class CardPanel extends JPanel {

  private final String cardColor;
  private final String cardText;
  private final String hintText;

  /**
   * The constructor method for the game card panel.
   *
   * @param cardLength The length of the card.
   * @param cardWidth  The width of the card.
   * @param cardColor  The color of the card.
   * @param cardText   The String that contains all ATK values of the card.
   * @param hintText   The String that contains the number of cards that it can be flipped when
   *                   clicked, if it is a grid cell and a card was currently selected.
   */
  public CardPanel(int cardLength, int cardWidth, String cardColor, String cardText,
                   String hintText) {
    this.cardColor = cardColor;
    this.cardText = cardText;
    this.hintText = hintText;
    setPreferredSize(new Dimension(cardLength, cardWidth));
    // set the card color
    switch (cardColor) {
      case "RED":
        setBackground(Color.RED);
        break;
      case "BLUE":
        setBackground(Color.BLUE);
        break;
      case "YELLOW":
        setBackground(Color.YELLOW);
        break;
      case "GRAY":
        setBackground(Color.GRAY);
        break;
      default:
        throw new IllegalArgumentException("Illegal card color.");
    }

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g2d.setFont(new Font("Arial", Font.ITALIC, 10));
    g2d.setColor(Color.BLACK);

    String[] attackVals = this.cardText.split("\\s");

    //hint drawing for empty cell if hints are enabled
    g2d.drawString(hintText, 10, getHeight() - 10);

    // check whether this card is an empty cell/ hole
    if (this.cardColor.equals("YELLOW") || this.cardColor.equals("GRAY")) {
      return;
    }
    if (attackVals.length != 5) {
      throw new IllegalArgumentException("Invalid card text: must contain exactly five values, " +
              "a card name and four ATK values.");
    }

    g2d.setFont(new Font("Arial", Font.BOLD, 20));
    FontMetrics fm = g2d.getFontMetrics();

    int margin = 10;

    int northX = (getWidth() - fm.stringWidth(attackVals[1])) / 2;
    int northY = margin + fm.getAscent();

    int southX = (getWidth() - fm.stringWidth(attackVals[2])) / 2;
    int southY = getHeight() - margin;

    int eastX = getWidth() - margin - fm.stringWidth(attackVals[3]);
    int eastY = getHeight() / 2 + fm.getAscent() / 2;

    int westX = margin;
    int westY = getHeight() / 2 + fm.getAscent() / 2;

    // draw the attack value at four direction
    g2d.drawString(attackVals[1], northX, northY);
    g2d.drawString(attackVals[2], southX, southY);
    g2d.drawString(attackVals[3], eastX, eastY);
    g2d.drawString(attackVals[4], westX, westY);

    //hint drawing
  }
}
