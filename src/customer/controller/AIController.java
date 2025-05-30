package customer.controller;

import javax.swing.JOptionPane;

import customer.gamefeatures.IPlayer;
import customer.model.ThreeTriosModel;
import customer.strategiccomputerplayer.Move;
import customer.strategiccomputerplayer.Strategy;
import customer.view.GameBoard;

/**
 * AI Controller for ThreeTrios game.
 */
public class AIController extends AbstractController {
  private final GameBoard view;
  private final ThreeTriosModel model;
  private final Strategy strategy;
  private final IPlayer player;

  /**
   * Constructor for AIController.
   *
   * @param view     The game customer.provider.view associated with this AI player.
   * @param model    The game customer.provider.model to interact with.
   * @param player   The AI player controlled by this customer.provider.controller.
   * @param strategy The strategy used by the AI to make decisions.
   */
  public AIController(GameBoard view, ThreeTriosModel model, IPlayer player, Strategy strategy) {
    super(model);
    this.view = view;
    this.model = model;
    this.player = player;
    this.strategy = strategy;
  }

  @Override
  public void setupGame() {
    // AI does not need direct interaction with the customer.provider.view
  }

  @Override
  public void executeTurn() {
    System.out.println("AI (" + player.getColor() + ") executing turn");

    Move bestMove = strategy.getBestMove(player);
    System.out.println("AI (" + player.getColor() + ") selected move: " + bestMove);

    if (bestMove != null) {
      int cardIndex = bestMove.getCardIdx();
      int row = bestMove.getPosition().x;
      int col = bestMove.getPosition().y;

      try {
        updateGameModel(player, cardIndex, row, col);
        System.out.println(
                "AI (" + player.getColor() + ") placed card at row " + row + ", col " + col);

        view.updateGameBoard(model.getPlayerA(), model.getPlayerB(), model);

        model.setCurrentPlayerMoved(true);

        System.out.println(
                "AI (" + player.getColor() + ") successfully played at (" + row + ", " + col + ")");
      } catch (IllegalArgumentException e) {
        System.err.println(
                "AI (" + player.getColor() + ") failed to place card: " + e.getMessage());
        JOptionPane.showMessageDialog(null,
                "AI (" + player.getColor() + ") failed to place card: " + e.getMessage());
      }
    } else {
      System.out.println("No valid moves for AI player: " + player.getColor());
      JOptionPane.showMessageDialog(
              null, "AI player " + player.getColor() +
                      " has no valid moves.");
    }

    System.out.println("AI (" + player.getColor() + ") ending turn");
  }

  @Override
  public void endTurn() {
    // AI do not need to ban any panel
    System.out.println("AI (" + player.getColor() + ") ending turn");
  }

  @Override
  public boolean isHumanControlled() {
    return false;
  }

  @Override
  public void onCellClicked(int row, int col) {
    // AIController do not handel grid click event
  }

  @Override
  public boolean onCardSelected(int cardIndex, String cardColor) {
    // AIController do not handel card selection event
    return false;
  }

  @Override
  public int hintTime(boolean keyPressed, boolean noCardSelected) {
    // AIController doesn't need hints since it is an AI itself
    return -1;
  }
}
