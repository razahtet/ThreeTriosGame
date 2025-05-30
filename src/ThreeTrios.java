import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

import customer.controller.Controller;
import customer.controller.HumanController;
import customer.gamefeatures.BattleType;
import customer.gamefeatures.IPlayer;
import customer.gamefeatures.ModType;
import customer.model.ThreeTriosGameModel;
import customer.model.ThreeTriosModel;
import customer.strategiccomputerplayer.CornerStrategy;
import customer.strategiccomputerplayer.MaxFlipStrategy;
import customer.strategiccomputerplayer.Strategy;
import customer.view.GameBoard;

/**
 * Runs the customer's (our) version of the ThreeTrios game and the customer's GUI.
 */
public final class ThreeTrios {
  /**
   * The main method that runs when the file is run.
   *
   * @param args Any input given to the console.
   */
  public static void main(String[] args) {
    ThreeTriosModel model = new ThreeTriosGameModel();
    // Ensure that the currentPlayerMoved is initially false
    System.out.println("In-game: Press H to toggle hints, to see how many of the cards on the\n " +
            "board the player can flip, which shows if and only if a card is selected in their\n " +
            "deck. When it moves to the next player's turn, they must toggle H again to see " +
            "their hints.\n");
    boolean validBattleModType = false;
    ModType mt = ModType.NORMAL;
    BattleType bt = null;
    // will keep asking the user for a valid battle type plus a modification type if there is one,
    // if the user does not give them
    while (!validBattleModType) {
      System.out.println("What type of battle do you want? \n- n for Normal\n- r for Reverse\n" +
              "- fa for fallen ace" + "\n- rfa for reverse and fallen ace\n" +
              "- add a \"s\" at the end of the string to add a modification where the player\n "
              + " can flip the opponent's card if the cards' numbers are the same in their facing \n"
              + "  directions when attacked");
      Scanner scanner = new Scanner(System.in);
      String battleModMode = scanner.nextLine();
      if (battleModMode.substring(battleModMode.length() - 1).
              equalsIgnoreCase("s")) {
        mt = ModType.SAME;
        battleModMode = battleModMode.substring(0, battleModMode.length() - 1);
      }
      if (battleModMode.equalsIgnoreCase("n")) {
        bt = BattleType.NORMAL;
      } else if (battleModMode.equalsIgnoreCase("r")) {
        bt = BattleType.REVERSE;
      } else if (battleModMode.equalsIgnoreCase("fa")) {
        bt = BattleType.ACE;
      } else if (battleModMode.equalsIgnoreCase("rfa")) {
        bt = BattleType.REVERSEANDACE;
      }
      if (bt != null) {
        validBattleModType = true;
      } else {
        System.out.println("Please type in a valid battle mode plus modification if there is one.");
      }
    }
    model.startGame(
            "src\\customer\\ConstructorFiles\\GridFile_10",
            "src\\customer\\ConstructorFiles\\CardFile_6",
            false
    );
    model.setCurrentPlayerMoved(false);
    // Create a single GameBoard instance
    GameBoard sharedView = new GameBoard(model, bt, mt);

    // Create player instances
    IPlayer player1 = model.getPlayerA();
    IPlayer player2 = model.getPlayerB();

    Strategy cornerStrategy = new CornerStrategy(model);
    Strategy maxFlipStrategy = new MaxFlipStrategy(model);

    Controller controller1 = new HumanController(sharedView, player1, model);
    Controller controller2 = new HumanController(sharedView, player2, model);

    // Create the main game window (JFrame)
    JFrame gameFrame = new JFrame("Three Trios Game");
    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gameFrame.setSize(1200, 800);
    gameFrame.setLayout(new BorderLayout());

    // Top panel to display the current player's turn
    JPanel playerStatusPanel = new JPanel();
    JLabel currentPlayerLabel = new JLabel("Current Player: RED");
    playerStatusPanel.add(currentPlayerLabel);
    sharedView.showCurrentPlayerTurn("Current Player: RED");
    gameFrame.add(playerStatusPanel, BorderLayout.NORTH);

    // Add the shared GameBoard's panels to the main frame
    gameFrame.add(sharedView.getLeftHandCardPanel(), BorderLayout.WEST);
    gameFrame.add(sharedView.getRightHandCardPanel(), BorderLayout.EAST);
    gameFrame.add(sharedView.getGridPanel(), BorderLayout.CENTER);

    // Make the game window visible
    SwingUtilities.invokeLater(() -> gameFrame.setVisible(true));

    // Start the game loop in a separate thread to avoid blocking the EDT
    new Thread(() -> startGameLoop(model, controller1, controller2, currentPlayerLabel,
            sharedView)).start();
  }

  /**
   * Starts the main game loop.
   *
   * @param model              The game customer.provider.model.
   * @param controller1        Controller for Player A (RED).
   * @param controller2        Controller for Player B (BLUE).
   * @param currentPlayerLabel Label to display the current player's turn.
   * @param sharedView         The shared GameBoard instance.
   */
  private static void startGameLoop(ThreeTriosModel model, Controller controller1,
                                    Controller controller2, JLabel currentPlayerLabel,
                                    GameBoard sharedView) {
    while (!model.isOver()) {
      // Get the current player
      String currentTurn = model.getCurrentPlayer();

      // Execute the turn only if the current player hasn't moved yet
      if (!model.hasCurrentPlayerMoved()) {
        if (currentTurn.equals("RED")) {
          System.out.println("Executing turn for player: RED");

          // Set the grid panel's customer.provider.controller to controller1
          SwingUtilities.invokeLater(() -> sharedView.getGridPanel().setController(controller1));
          // Execute RED player's turn (AI)
          // controller2.endTurn(); //this is not needed
          // controller1.executeTurn();
        } else if (currentTurn.equals("BLUE")) {
          System.out.println("Executing turn for player: BLUE");

          // Set the grid panel's customer.provider.controller to controller2
          SwingUtilities.invokeLater(() -> sharedView.getGridPanel().setController(controller2));
          // Execute BLUE player's turn (Human)
          // controller1.endTurn(); this is not needed
          // controller2.executeTurn();
        }
      }

      // Wait for the current player to make a move
      waitForPlayerMove(model);

      // After the player has made a move, switch to the next player
      if (model.hasCurrentPlayerMoved()) {
        model.switchToNextPlayer();
        model.setCurrentPlayerMoved(false);
        System.out.println("Next player's turn: " + model.getCurrentPlayer());
        currentPlayerLabel.setText("Current Player: " + model.getCurrentPlayer());

        // Update the game board customer.provider.view in the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> sharedView.updateGameBoard(model.getPlayerA(),
                model.getPlayerB(), model));
      }
    }

    // Game over, display the winner
    IPlayer winner = model.getWinner();
    if (winner == null) {
      JOptionPane.showMessageDialog(null, "Game Over! Tie game!");
    } else {
      JOptionPane.showMessageDialog(null, "Game Over! The winner is: " +
              winner.getColor());
    }
  }

  /**
   * Enables or disables interaction for a given panel.
   *
   * @param panel  The panel to modify.
   * @param enable True to enable, false to disable.
   */
  private static void enablePlayerInteraction(JPanel panel, boolean enable) {
    if (panel != null) {
      panel.setEnabled(enable);
      for (Component component : panel.getComponents()) {
        component.setEnabled(enable);
      }
      System.out.println("Panel " + (panel.getName() != null ? panel.getName() : "Unnamed") +
              " enabled: " + enable);
    } else {
      System.out.println("Attempted to enable a null panel.");
    }
  }

  /**
   * Waits until the current player has made a move.
   *
   * @param model The game customer.provider.model.
   */
  private static void waitForPlayerMove(ThreeTriosModel model) {
    while (!model.hasCurrentPlayerMoved()) {
      try {
        Thread.sleep(100); // Wait for the player to complete their move
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RuntimeException(e);
      }
    }
  }
}
