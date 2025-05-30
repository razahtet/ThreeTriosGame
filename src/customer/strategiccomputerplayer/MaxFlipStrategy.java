package customer.strategiccomputerplayer;

import java.awt.Point;
import java.util.List;

import customer.gamefeatures.Card;
import customer.gamefeatures.IPlayer;
import customer.gamefeatures.Player;
import customer.model.ThreeTriosModel;

/**
 * Implements the best card selection strategy for the AI.
 */
public class MaxFlipStrategy extends AbstractStrategy {

  // Current best move for this round
  private Move bestMove;

  /**
   * Constructor for MaxFlipStrategy.
   *
   * @param model The current game customer.provider.model.
   */
  public MaxFlipStrategy(ThreeTriosModel model) {
    super(model);
    this.bestMove = null;
  }

  @Override
  public Move getBestMove(IPlayer player) {
    // Reset bestMove for the new computation
    this.bestMove = null;

    // Get the player's hand
    List<Card> hand = player.getHand();
    if (hand == null || hand.isEmpty()) {
      throw new IllegalStateException("Player's hand is empty, no cards to play.");
    }

    // Get grid dimensions
    int rows = model.getGameGrid().getRow();
    int cols = model.getGameGrid().getCol();
    int originalScore = model.countCardOnGrid(player.getColor());

    // Iterate through each card in hand
    for (int i = 0; i < hand.size(); i++) {
      for (int rowNum = 0; rowNum < rows; rowNum++) {
        for (int colNum = 0; colNum < cols; colNum++) {
          // Check if the card can be played at (colNum, rowNum)
          if (ableToPlay(colNum, rowNum)) {
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
              System.err.println("Copied player's hand does not have index " + i + ", hand size: "
                      + copyPlayer.getHand().size());
              continue; // Skip this card
            }

            // Get the card object from the copied player's hand before playing
            Card cardToPlay = copyPlayer.getHand().get(i);
            System.out.println("Simulating move with card index: " + i + ", card name: "
                    + cardToPlay.getName());

            try {
              // Play the card on the copied customer.provider.model
              copyModel.playToGrid(i, colNum, rowNum, copyPlayer);
              System.out.println("Simulated placing card " + cardToPlay.getName() +
                      " at (" + colNum + ", " + rowNum + ")");

              // Simulate battle (if applicable)
              copyModel.battle(cardToPlay, colNum, rowNum, player.getColor());
              System.out.println("Simulated battle for card " + cardToPlay.getName());

              // Calculate score earned
              int scoreEarned = copyModel.countCardOnGrid(player.getColor()) - originalScore;
              if (isCorner(colNum, rowNum)) {
                scoreEarned += 1;
              }
              System.out.println("Score earned: " + scoreEarned);

              // Calculate flipped probability
              int flippedProb = this.getBeFlippedProbability(cardToPlay, colNum, rowNum);
              System.out.println("Card " + cardToPlay.getName() + " at (" + colNum + ", " + rowNum
                      + ") has flipped probability: " + flippedProb);

              // Create Move object for the current move
              Move currentMove = new Move(new Point(rowNum, colNum), i, scoreEarned, flippedProb);
              System.out.println("Evaluated move: " + currentMove);

              // Compare and update the bestMove if currentMove is better
              if (this.bestMove == null || currentMove.compareTo(bestMove) > 0) {
                this.bestMove = currentMove;
                System.out.println("New best move: " + bestMove);
              }
            } catch (IllegalStateException e) {
              // Handle the case where the copied player's hand does not have the card at index i
              System.err.println("Failed to simulate move for card index " + i + ": " +
                      e.getMessage());
              continue; // Skip this move and continue with other possibilities
            }
          }
        }
      }
    }

    // If no bestMove was found, handle accordingly by
    // playing the first card to the first available position
    if (this.bestMove == null) {
      System.out.println("No valid moves found using MaxFlipStrategy. " +
              "Falling back to first available move.");

      // Check if the player's hand is not empty
      if (hand.isEmpty()) {
        throw new IllegalStateException("Player's hand is empty, no cards to play.");
      }

      // Select the first card in the hand (index 0)
      int firstCardIndex = 0;
      Card firstCard = hand.get(firstCardIndex);
      System.out.println("Selected first card: " + firstCard.getName());

      // Find the first available position from top-left
      int[] firstAvailablePosition = findFirstAvailablePosition();
      if (firstAvailablePosition != null) {
        int row = firstAvailablePosition[0];
        int col = firstAvailablePosition[1];
        System.out.println("First available position found at (" + col + ", " + row + ")");

        // Simulate the move on a copy of the customer.provider.model
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
        if (firstCardIndex >= copyPlayer.getHand().size()) {
          System.err.println("Copied player's hand does not have index " + firstCardIndex +
                  ", hand size: " + copyPlayer.getHand().size());
          throw new IllegalStateException("No valid fallback moves available for the player.");
        }

        // Get the first card from the copied player's hand before playing
        Card fallbackCard = copyPlayer.getHand().get(firstCardIndex);
        System.out.println("Simulating fallback move with card index: " + firstCardIndex +
                ", card name: " + fallbackCard.getName());

        try {
          // Play the first card on the copied customer.provider.model
          copyModel.playToGrid(firstCardIndex, col, row, copyPlayer);
          System.out.println("Simulated placing fallback card " + fallbackCard.getName() +
                  " at (" + col + ", " + row + ")");

          // Simulate battle (if applicable)
          copyModel.battle(fallbackCard, col, row, player.getColor());
          System.out.println("Simulated battle for fallback card " + fallbackCard.getName());

          // Calculate score earned
          int scoreEarned = copyModel.countCardOnGrid(player.getColor()) -
                  model.countCardOnGrid(player.getColor());
          if (isCorner(col, row)) {
            scoreEarned += 1;
          }
          System.out.println("Score earned from fallback move: " + scoreEarned);

          // Calculate flipped probability
          int flippedProb = this.getBeFlippedProbability(fallbackCard, col, row);
          System.out.println("Fallback card " + fallbackCard.getName() +
                  " at (" + col + ", " + row + ") has flipped probability: " + flippedProb);

          // Create Move object for the fallback move
          Move fallbackMove = new Move(new Point(row, col),
                  firstCardIndex, scoreEarned, flippedProb);
          System.out.println("Fallback move created: " + fallbackMove);

          this.bestMove = fallbackMove;
        } catch (IllegalStateException e) {
          System.err.println("Failed to simulate fallback move: " + e.getMessage());
          throw new IllegalStateException("No valid fallback moves available for the player.");
        }
      } else {
        // No available positions to play
        throw new IllegalStateException("No available positions to play for the player.");
      }
    }

    return bestMove;
  }

  /**
   * Finds the first available position on the grid starting from top-left.
   *
   * @return An array containing [row, col] of the first available position, or null if none.
   */
  private int[] findFirstAvailablePosition() {
    int rows = model.getGameGrid().getRow();
    int cols = model.getGameGrid().getCol();

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (ableToPlay(col, row)) {
          System.out.println("Found available position at (" + col + ", " + row + ")");
          return new int[]{row, col};
        }
      }
    }
    System.out.println("No available positions found on the grid.");
    return null; // No available position
  }
}
