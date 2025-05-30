package customer.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.GridLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import customer.controller.Controller;
import customer.gamefeatures.BattleType;
import customer.gamefeatures.Card;
import customer.gamefeatures.IGameGrid;
import customer.gamefeatures.ModType;
import customer.model.ThreeTriosModel;

/**
 * The class that creates the customer.provider.view of the grid panel.
 */
public class GridPanel extends JPanel {
  private IGameGrid gameGrid;
  private final int cardLength;
  private final int cardWidth;
  private Controller controller;
  private final ThreeTriosModel model;
  private final BattleType battleType;
  private final ModType modType;

  /**
   * Constructor for GridPanel.
   *
   * @param gameGrid   The current game grid.
   * @param cardLength The length of one card in the grid.
   * @param cardWidth  The width of one card in the grid.
   */
  public GridPanel(IGameGrid gameGrid, int cardLength, int cardWidth, ThreeTriosModel model,
                   BattleType battleType, ModType modType) {
    this.gameGrid = gameGrid;
    this.cardLength = cardLength;
    this.cardWidth = cardWidth;
    this.model = model;
    this.battleType = battleType;
    this.modType = modType;
    setLayout(new GridLayout(gameGrid.getRow(), gameGrid.getCol(), 0, 0));
    initializeGrid();
  }

  /**
   * Sets the event listener for the grid panel.
   *
   * @param controller The event listener to set.
   */
  public void setController(Controller controller) {
    this.controller = controller;
  }

  protected void shouldShowHint() {
    controller.hintTime(true, false);
    this.updateGrid(gameGrid);
  }

  /**
   * Initializes the grid by adding CardPanels to the grid.
   */
  private void initializeGrid() {
    removeAll(); // Clear existing components

    int hintCardIndex = -1;
    //numCardsCanFlip determination
    if (controller != null && controller.isHumanControlled() && !model.isOver()) {
      hintCardIndex = controller.hintTime(false, false);
    }

    Card[][] cardInGrid = gameGrid.getGrid();
    int rows = gameGrid.getRow();
    int cols = gameGrid.getCol();

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Card card = cardInGrid[row][col];
        //should show the hint on the grid cell
        String cardHintText = "";
        if (hintCardIndex != -1 && card != null && card.getName().equals("C")) {
          if (model.getCurrentPlayer().equalsIgnoreCase("red")) {
            cardHintText = "" + model.getNumCardsCanFlip(hintCardIndex, row, col,
                    model.getPlayerA());
          } else if (model.getCurrentPlayer().equalsIgnoreCase("blue")) {
            cardHintText = "" + model.getNumCardsCanFlip(hintCardIndex, row, col,
                    model.getPlayerB());
          } else {
            throw new IllegalArgumentException("This player doesn't exist in the model.");
          }
        }
        if (card != null) {
          card.setBattleType(battleType);
          card.setModType(modType);
        }
        CardPanel cardPanel = new CardPanel(
                cardLength,
                cardWidth,
                card != null ? card.getColor() : "EMPTY",
                card != null ? card.toString() : "",
                cardHintText
        );
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        final int currentRow = row;
        final int currentCol = col;

        for (MouseListener ma : cardPanel.getMouseListeners()) {
          cardPanel.removeMouseListener(ma);
        }

        cardPanel.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            if (controller != null && controller.isHumanControlled()) {
              controller.onCellClicked(currentRow, currentCol);
            }
          }
        });

        add(cardPanel);
      }
    }

    revalidate();
    repaint();
  }

  /**
   * Updates the grid customer.provider.view with the latest GameGrid data.
   *
   * @param updatedGrid The updated game grid.
   */
  public void updateGrid(IGameGrid updatedGrid) {
    if (updatedGrid == null) {
      throw new IllegalArgumentException("Updated grid cannot be null.");
    }

    this.gameGrid = updatedGrid;

    initializeGrid();
  }

  /**
   * Optionally, you can provide a method to get the current GameGrid.
   *
   * @return The current game grid.
   */
  public IGameGrid getGameGrid() {
    return this.gameGrid;
  }
}
