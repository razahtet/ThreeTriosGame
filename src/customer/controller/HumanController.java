package customer.controller;

import java.awt.Component;

import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import customer.gamefeatures.IPlayer;
import customer.model.ThreeTriosModel;
import customer.view.GameBoard;

/**
 * Controller for a human player in ThreeTrios game.
 */
public class HumanController extends AbstractController implements GameEventListener {
  private final GameBoard view;
  private final IPlayer player;
  private final ThreeTriosModel model;
  private int selectIdx;
  private boolean hintShowing;

  /**
   * Constructor for customer.provider.controller.
   *
   * @param view   Current game customer.provider.view.
   * @param player The player controlled by this customer.provider.controller.
   * @param model  The current game customer.provider.model.
   */
  public HumanController(GameBoard view, IPlayer player, ThreeTriosModel model) {
    super(model);
    this.view = view;
    this.player = player;
    this.model = model;
    this.selectIdx = -1; // Initialize to no card selected
    this.hintShowing = false;
    setupGame();
  }

  /**
   * Called when a cell in the grid is clicked.
   * Attempts to play a card if one is selected.
   *
   * @param row The row of the clicked cell.
   * @param col The column of the clicked cell.
   */
  @Override
  public void onCellClicked(int row, int col) {
    if (view.getGridPanel().isEnabled()) {
      // Handle grid clicking event
      System.out.println("Cell clicked at row " + row + ", col " + col);
      if (!checkGameOver()) {
        if (selectIdx == -1) {
          // No card selected
          JOptionPane.showMessageDialog(null, "Please select a " +
                  "card from your hand first.");
        } else {
          try {
            // Update game customer.provider.model
            updateGameModel(player, selectIdx, row, col);
            model.setCurrentPlayerMoved(true);
            selectIdx = -1; // Reset selected index after playing the card
            hintShowing = false; // Should not show any hints to the next player at first
            // Update the UI after placing the card
            SwingUtilities.invokeLater(() -> view.updateGameBoard(model.getPlayerA(),
                    model.getPlayerB(), model));

            System.out.println("Card successfully played to grid by player " + player.getColor());
          } catch (IllegalArgumentException e) {
            // If the placement is invalid, notify the user
            JOptionPane.showMessageDialog(null, e.getMessage());
            //update the ui so that the player can start selecting a card and playing it to a cell
            selectIdx = -1;
            SwingUtilities.invokeLater(() -> view.updateGameBoard(model.getPlayerA(),
                    model.getPlayerB(), model));
          }
        }
      }
    }
  }

  /**
   * Called when a card is selected from the hand.
   * Allows the player to select or deselect a card.
   *
   * @param cardIndex The index of the selected card.
   * @param cardColor The color of the selected card.
   */
  @Override
  public boolean onCardSelected(int cardIndex, String cardColor) {
    if (!checkGameOver()) {
      if (!model.getCurrentPlayer().equalsIgnoreCase(cardColor)) {
        // Ensure provider.players can only select their own cards
        JOptionPane.showMessageDialog(null,
                "You cannot select cards from the other player.");
        // selectIdx = -1;
        return false;
      } else if (selectIdx == cardIndex) {
        // Deselect card if it's already selected
        System.out.println("Card deselected at index " + cardIndex);
        selectIdx = -1;
      } else {
        // Handle card selection event
        System.out.println("Card selected at index " + cardIndex + " by player " + cardColor);
        selectIdx = cardIndex;
      }
      view.getGridPanel().updateGrid(model.getGameGrid());
    } else {
      return false;
    }
    return true;
  }

  @Override
  public int hintTime(boolean keyPressed, boolean noCardSelected) {
    int toReturn = -1;
    if (!checkGameOver()) {
      if (keyPressed) {
        if (this.hintShowing) {
          this.hintShowing = false;
        } else {
          this.hintShowing = true;
          JOptionPane.showMessageDialog(null, "Hints are being shown when" +
                  " a card is selected now for Player " + model.getCurrentPlayer() + "!");
          toReturn = selectIdx;
        }
      } else if (!this.hintShowing) {
        System.out.println("no hint");
      } else if (!noCardSelected) {
        toReturn = selectIdx;
      }
    }
    return toReturn;
  }

  /**
   * Sets up the game by registering the customer.provider.controller as the listener
   * for relevant panels in the game board.
   */
  @Override
  public void setupGame() {
    // 仅设置手牌面板的控制器，不设置网格面板的控制器
    if (player.getColor().equals("RED")) {
      view.getLeftHandCardPanel().setController(this);
      System.out.println("Controller set for RED player's hand cards.");
    } else if (player.getColor().equals("BLUE")) {
      view.getRightHandCardPanel().setController(this);
      System.out.println("Controller set for BLUE player's hand cards.");
    }
  }

  /**
   * Executes the current player's turn, allowing interaction with the grid
   * and their hand cards. Disables interaction for the other player.
   */
  @Override
  public void executeTurn() {
    System.out.println("Executing turn for player: " + player.getColor());

    // Enable panel interaction to allow the player to make their move
    enablePanelInteraction(view.getGridPanel());

    if (player.getColor().equals("RED")) {
      enablePanelInteraction(view.getLeftHandCardPanel());
      disablePanelInteraction(view.getRightHandCardPanel());
    } else if (player.getColor().equals("BLUE")) {
      enablePanelInteraction(view.getRightHandCardPanel());
      disablePanelInteraction(view.getLeftHandCardPanel());
    }

    // Display a label or update the game board to show that it is the current player's turn
    view.showCurrentPlayerTurn(player.getColor() +
            ": It's your turn! Please select a card and place it on the grid.");

    // Debug output to check the state of panels
    System.out.println("RED Player Hand Panel Enabled: " + view.getLeftHandCardPanel().isEnabled());
    System.out.println("BLUE Player Hand Panel Enabled: " +
            view.getRightHandCardPanel().isEnabled());
    System.out.println("Grid Panel Enabled: " + view.getGridPanel().isEnabled());
  }

  /**
   * Ends the current player's turn, disabling interaction with the grid
   * and the player's hand cards.
   */
  @Override
  public void endTurn() {
    System.out.println("Ending turn for player: " + player.getColor());

    // Disable grid panel interaction to prevent further actions
    disablePanelInteraction(view.getGridPanel());

    // Disable hand card selection for the current player
    if (player.getColor().equals("RED")) {
      disablePanelInteraction(view.getLeftHandCardPanel());
      enablePanelInteraction(view.getRightHandCardPanel());
    } else if (player.getColor().equals("BLUE")) {
      disablePanelInteraction(view.getRightHandCardPanel());
      enablePanelInteraction(view.getLeftHandCardPanel());
    }

    // Debug output to check the state of panels
    System.out.println("RED Player Hand Panel Enabled After End Turn: " +
            view.getLeftHandCardPanel().isEnabled());
    System.out.println("BLUE Player Hand Panel Enabled After End Turn: " +
            view.getRightHandCardPanel().isEnabled());
  }

  @Override
  public boolean isHumanControlled() {
    return true;
  }

  /**
   * Enables the interaction between the player and the specified panel.
   *
   * @param panel The panel that the player wants to interact with.
   */
  private void enablePanelInteraction(JPanel panel) {
    if (panel == null) {
      System.out.println("Attempted to enable interaction for a null panel.");
      return;
    }
    Component[] components = panel.getComponents();
    for (Component component : components) {
      component.setEnabled(true);
    }
    panel.setEnabled(true);
    System.out.println("Enabled interaction for panel: " +
            (panel.getName() != null ? panel.getName() : "Unnamed"));
  }

  /**
   * Disables the interaction between the player and the specified panel.
   *
   * @param panel The panel that the player wants to interact with.
   */
  private void disablePanelInteraction(JPanel panel) {
    if (panel == null) {
      System.out.println("Attempted to disable interaction for a null panel.");
      return;
    }
    Component[] components = panel.getComponents();
    for (Component component : components) {
      component.setEnabled(false);
    }
    panel.setEnabled(false);
    System.out.println("Disabled interaction for panel: " +
            (panel.getName() != null ? panel.getName() : "Unnamed"));
  }

  private boolean checkGameOver() {
    if (model.isOver()) {
      String message = "Game Over! Tie Game!";
      if (model.getWinner() != null) {
        message = "Game Over! The winner is " + model.getWinner().getColor();
      }
      JOptionPane.showMessageDialog(null, message);
      return true;
    }
    return false;
  }
}
