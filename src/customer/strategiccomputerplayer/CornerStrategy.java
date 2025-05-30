package customer.strategiccomputerplayer;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import customer.gamefeatures.IPlayer;
import customer.gamefeatures.Player;
import customer.model.ThreeTriosModel;

/**
 * The strategy class that implements the corner strategy,
 * which is to place a card at the corner if there is enough space.
 */
public class CornerStrategy extends AbstractStrategy {

  Move bestMove;

  /**
   * Constructor for CornerStrategy.
   *
   * @param model The current game customer.provider.model.
   */
  public CornerStrategy(ThreeTriosModel model) {
    super(model);
    bestMove = null;
  }

  @Override
  public Move getBestMove(IPlayer player) {
    // Initialize list to hold best corner moves
    List<Move> cornerMoves = new ArrayList<>();

    // Get best move for each corner
    Move topLeft = getBestMoveAtCorner(player, "topleft");
    Move topRight = getBestMoveAtCorner(player, "topright");
    Move botLeft = getBestMoveAtCorner(player, "botleft");
    Move botRight = getBestMoveAtCorner(player, "botright");

    // Add valid corner moves to the list
    if (topLeft != null && topLeft.getScore() >= 0) {
      cornerMoves.add(topLeft);
    }
    if (topRight != null && topRight.getScore() >= 0) {
      cornerMoves.add(topRight);
    }
    if (botLeft != null && botLeft.getScore() >= 0) {
      cornerMoves.add(botLeft);
    }
    if (botRight != null && botRight.getScore() >= 0) {
      cornerMoves.add(botRight);
    }

    if (!cornerMoves.isEmpty()) {
      // Find the best move among corner moves
      Move bestCornerMove = cornerMoves.get(0);
      for (Move move : cornerMoves) {
        if (move.compareTo(bestCornerMove) > 0) {
          bestCornerMove = move;
        }
      }
      this.bestMove = bestCornerMove;
      System.out.println("Best Corner Move: " + bestCornerMove);
    } else {
      // If no corner moves available, use BestCardToChoose
      MaxFlipStrategy bestCardStrategy = new MaxFlipStrategy(model);
      this.bestMove = bestCardStrategy.getBestMove(player);
      System.out.println("No corner moves available. Best Card Move: " + bestMove);
    }

    return bestMove;
  }

  /**
   * Get the best move at the given corner.
   *
   * @param player The player for this round.
   * @param corner The corner to play cards to.
   * @return The best move for this corner, or null if no move is possible.
   */
  public Move getBestMoveAtCorner(IPlayer player, String corner) {
    int row = 0;
    int col = 0;
    switch (corner.toLowerCase()) {
      case "topleft":
        break;
      case "topright":
        col = model.getGameGrid().getCol() - 1;
        break;
      case "botleft":
        row = model.getGameGrid().getRow() - 1;
        break;
      case "botright":
        row = model.getGameGrid().getRow() - 1;
        col = model.getGameGrid().getCol() - 1;
        break;
      default:
        throw new IllegalArgumentException("Unknown corner: " + corner);
    }

    Move bestMoveAtCorner = null;

    // Iterate through each card in player's hand
    for (int i = 0; i < player.getHand().size(); i++) {
      // Create a copy of the game customer.provider.model to simulate the move
      ThreeTriosModel copyModel = model.copyGameModel();

      // Determine the corresponding copied player in copyModel
      Player copyPlayer;
      if (player.getColor().equals(model.getPlayerA().getColor())) {
        copyPlayer = (Player) copyModel.getPlayerA();
      } else if (player.getColor().equals(model.getPlayerB().getColor())) {
        copyPlayer = (Player) copyModel.getPlayerB();
      } else {
        throw new IllegalArgumentException("Unknown player color: " + player.getColor());
      }

      // Ensure the copied player's hand has enough cards
      if (i >= copyPlayer.getHand().size()) {
        System.err.println("Copied player's hand does not have index " + i);
        continue; // Skip this card
      }

      try {
        // Play the card on the copied customer.provider.model
        copyModel.playToGrid(i, col, row, copyPlayer);
      } catch (IllegalStateException e) {
        // Handle the case where the copied player's hand does not have the card at index i
        System.err.println("Attempted to access card index " + i + " in copied player's " +
                "hand of size " + copyPlayer.getHand().size());
        continue; // Skip this card
      }

      copyModel.battle(player.getHand().get(i), col, row, player.getColor());
      int scoreEarned = copyModel.countCardOnGrid(player.getColor()) -
              model.countCardOnGrid(player.getColor());
      if (isCorner(col, row)) {
        scoreEarned += 1;
      }
      int flippedProb = this.getBeFlippedProbability(player.getHand().get(i), col, row);
      Move currentMove = new Move(new Point(col, row), i, scoreEarned, flippedProb);

      if (bestMoveAtCorner == null || currentMove.compareTo(bestMoveAtCorner) > 0) {
        bestMoveAtCorner = currentMove;
      }
    }

    return bestMoveAtCorner;
  }
}
