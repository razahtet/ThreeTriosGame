package customer.view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFrame;

import customer.gamefeatures.BattleType;
import customer.gamefeatures.IPlayer;
import customer.gamefeatures.ModType;
import customer.model.ThreeTriosModel;

/**
 * Class that represents the entire game board panel.
 * It contains the game grid and the hand card panels for both provider.players.
 */
public class GameBoard extends JPanel {
  private final GridPanel gridPanel;
  private final HandCardPanel leftHandCardPanel;
  private final HandCardPanel rightHandCardPanel;
  private final JLabel currentPlayerLabel; // Label to display the current player's turn

  /**
   * Constructor for GameBoard.
   * Initializes all sub-panels and sets up the layout.
   *
   * @param gameModel The current game customer.provider.model.
   */
  public GameBoard(ThreeTriosModel gameModel, BattleType battleType, ModType
          modType) {
    // Initialize the grid panel with the game grid from the customer.provider.model
    this.gridPanel = new GridPanel(gameModel.getGameGrid(), 100, 100,
            gameModel, battleType, modType);
    this.gridPanel.setName("GridPanel"); // Set name for debugging

    // Initialize the left hand card panel for Player A (e.g., RED)
    this.leftHandCardPanel = new HandCardPanel(
            gameModel.getPlayerA().getHand(),
            gameModel.getPlayerA().getColor(),
            100,
            800, battleType, modType
    );
    this.leftHandCardPanel.setName("LeftHandCardPanel"); // Set name for debugging

    // Initialize the right hand card panel for Player B (e.g., BLUE)
    this.rightHandCardPanel = new HandCardPanel(
            gameModel.getPlayerB().getHand(),
            gameModel.getPlayerB().getColor(),
            100,
            800, battleType, modType
    );
    this.rightHandCardPanel.setName("RightHandCardPanel"); // Set name for debugging

    // Initialize the label to display the current player's turn
    this.currentPlayerLabel = new JLabel("Game not started yet");
    this.currentPlayerLabel.setName("CurrentPlayerLabel"); // Set name for debugging
    // Set the layout manager and add all sub-panels to the GameBoard
    this.gridPanel.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        // This is not needed, we already checked when key h is pressed in keyReleased.
      }

      @Override
      public void keyPressed(KeyEvent e) {
        // This is not needed, we already checked when key h is pressed in keyReleased.
      }

      /**
       * If the key h is pressed, the gridPanel should either hide or show hints for the player.
       * @param e the event to be processed
       */
      @Override
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 72) {
          gridPanel.shouldShowHint();
        }
      }
    });
    this.gridPanel.setFocusable(true);
    setLayout(new BorderLayout());
    add(currentPlayerLabel, BorderLayout.NORTH);
    add(gridPanel, BorderLayout.CENTER);
    add(leftHandCardPanel, BorderLayout.WEST);
    add(rightHandCardPanel, BorderLayout.EAST);
  }

  /**
   * Retrieves the grid panel.
   *
   * @return The grid panel.
   */
  public GridPanel getGridPanel() {
    return gridPanel;
  }

  /**
   * Retrieves the left hand card panel (Player A).
   *
   * @return The left hand card panel.
   */
  public HandCardPanel getLeftHandCardPanel() {
    return leftHandCardPanel;
  }

  /**
   * Retrieves the right hand card panel (Player B).
   *
   * @return The right hand card panel.
   */
  public HandCardPanel getRightHandCardPanel() {
    return rightHandCardPanel;
  }

  /**
   * Creates a JFrame containing the entire game board.
   *
   * @return The JFrame containing the game.
   */
  public JFrame createGameFrame() {
    JFrame frame = new JFrame("Three Trios Game");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1200, 800);

    // Add this GameBoard instance to the frame
    frame.add(this);

    return frame;
  }

  /**
   * Updates the game board by refreshing the grid and both provider.players' hand cards.
   *
   * @param playerA The first player (e.g., RED).
   * @param playerB The second player (e.g., BLUE).
   * @param model   The current game customer.provider.model to update the grid.
   */
  public void updateGameBoard(IPlayer playerA, IPlayer playerB, ThreeTriosModel model) {
    // Update the hand cards for both provider.players
    leftHandCardPanel.updateHandCards(playerA.getHand());
    rightHandCardPanel.updateHandCards(playerB.getHand());

    // Update the game grid
    gridPanel.updateGrid(model.getGameGrid());

    // Refresh the UI
    revalidate();
    repaint();
  }

  /**
   * Displays a message indicating which player's turn it is.
   *
   * @param message The message to display.
   */
  public void showCurrentPlayerTurn(String message) {
    currentPlayerLabel.setText(message);
  }
}
