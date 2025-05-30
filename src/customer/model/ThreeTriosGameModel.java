package customer.model;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import customer.fileoperation.CardFileReader;
import customer.fileoperation.ConfigurationReader;
import customer.gamefeatures.Card;
import customer.gamefeatures.GameGrid;
import customer.gamefeatures.IGameGrid;
import customer.gamefeatures.IPlayer;
import customer.gamefeatures.Player;

/**
 * Game customer.provider.model of the Three Trios game, implements Interface ThreeTriosModel.
 */
public class ThreeTriosGameModel implements ThreeTriosModel {

  private Random seed;
  private boolean isGameStart;
  private IPlayer playerA;
  private IPlayer playerB;
  private IGameGrid gameGrid;
  private boolean playerAMoveInThisRound;
  private boolean playerBMoveInThisRound;
  private int numOfFlips;
  private String currentTurn;
  private List<Card> originalCards;

  /**
   * Constructor for the ThreeTriosGameModel, initially set the
   * isGameStart, playerAMoveInThisRound, and playerBMoveInThisRound as False.
   */
  public ThreeTriosGameModel() {
    this.isGameStart = false;
    this.playerAMoveInThisRound = false;
    this.playerBMoveInThisRound = false;
    this.currentTurn = "";
  }

  /**
   * Constructor for the ThreeTriosGameModel, initially set the
   * isGameStart, playerAMoveInThisRound, and playerBMoveInThisRound as False.
   *
   * @param seed Random object to shuffle the card.
   */
  public ThreeTriosGameModel(Random seed) {
    this();
    this.seed = seed;
  }

  /**
   * Constructor that makes a copy of another ThreeTriosGameModel.
   *
   * @param another A ThreeTriosGameModel object.
   */
  public ThreeTriosGameModel(ThreeTriosGameModel another) {
    this.isGameStart = another.isGameStart;
    this.playerAMoveInThisRound = another.playerAMoveInThisRound;
    this.playerBMoveInThisRound = another.playerBMoveInThisRound;
    this.playerA = new Player((Player) another.playerA);
    this.playerB = new Player((Player) another.playerB);
    this.seed = another.seed;
    this.gameGrid = new GameGrid((GameGrid) another.gameGrid);
    this.numOfFlips = 0;
    this.currentTurn = another.currentTurn;
    this.originalCards = another.originalCards;
  }

  @Override
  public void startGame(String gridFilePath, String cardFilePath, boolean shuffle) {
    if (isGameStart) {
      throw new IllegalStateException("Game has already started.");
    }
    this.isGameStart = true;
    this.gameGrid = new ConfigurationReader().buildGridUsingConfigFile(gridFilePath);
    this.playerA = new Player("RED");
    this.playerB = new Player("BLUE");

    // get all game card using CardFileReader
    List<Card> allCards = new CardFileReader().getHandCardUsingConfigFile(cardFilePath,
            "RED");
    this.originalCards = allCards;
    // check if the card number is at least N+1/2
    if (allCards.size() < (gameGrid.getCells() + 1)) {
      throw new IllegalArgumentException(
              "Total number of cards in this game should be at least " +
                      "initial cell number added by one and then divided by two");
    }

    // shuffle cards if needed
    if (shuffle) {
      if (seed == null) {
        this.seed = new Random();
      }
      Collections.shuffle(allCards, seed);
    }

    int cardsPerPlayer = (this.gameGrid.getCells() + 1) / 2;
    int counter = 0;

    // distribute cards to player A and B
    for (int i = 0; i < cardsPerPlayer; i++) {
      Card cardA = allCards.get(counter);
      playerA.addCardsToHand(cardA);
      counter += 1;
      Card cardB = allCards.get(counter);
      cardB.reverseColor();
      playerB.addCardsToHand(cardB);
      counter += 1;
    }

    this.currentTurn = "RED";
  }

  @Override
  public void playToGrid(int cardIdx, int x, int y, IPlayer player) {
    hasGameStartedOrOver(true);
    String playerColor = player.getColor();
    // check whether this player has already played in this round.
    if (playerColor.equals("RED") && playerAMoveInThisRound) {
      throw new IllegalStateException(
              "Player A has already moved in this round, its player B turn.");
    }
    if (playerColor.equals("BLUE") && playerBMoveInThisRound) {
      throw new IllegalStateException(
              "Player B has already moved in this round, its player A turn.");
    }
    // get card from the player's hand
    Card card = player.getCardFromHand(cardIdx);
    this.gameGrid.playToGrid(card, y, x);
    //only removes the card, if no IllegalArgumentException was thrown from playToGrid
    // update player status in this up.
    if (playerColor.equals("RED")) {
      this.getPlayerA().getHand().remove(card);
      this.playerAMoveInThisRound = true;
    } else {
      this.getPlayerB().getHand().remove(card);
      this.playerBMoveInThisRound = true;
    }
  }

  @Override
  public void battle(Card card, int x, int y, String cardColor) {
    hasGameStartedOrOver(false);
    // Battle the east card
    if (this.gameGrid.hasCard(y + 1, x)) {
      Card cardAtEast = this.gameGrid.getGrid()[x][y + 1];
      if (card.compare(cardAtEast, "east")) {
        cardAtEast.reverseColor();
        this.numOfFlips += 1;
        this.gameGrid.getGrid()[x][y + 1] = cardAtEast;
        // Recursively battle the next card in the east direction
        battle(cardAtEast, x, y + 1, cardColor);
      }
    }
    // Battle the west card
    if (this.gameGrid.hasCard(y - 1, x)) {
      Card cardAtWest = this.gameGrid.getGrid()[x][y - 1];
      if (card.compare(cardAtWest, "west")) {
        cardAtWest.reverseColor();
        this.numOfFlips += 1;
        this.gameGrid.getGrid()[x][y - 1] = cardAtWest;
        // Recursively battle the next card in the west direction
        battle(cardAtWest, x, y - 1, cardColor);
      }
    }

    // Battle the north card
    if (this.gameGrid.hasCard(y, x - 1)) {
      Card cardAtNorth = this.gameGrid.getGrid()[x - 1][y];
      if (card.compare(cardAtNorth, "north")) {
        cardAtNorth.reverseColor();
        this.numOfFlips += 1;
        this.gameGrid.getGrid()[x - 1][y] = cardAtNorth;
        // Recursively battle the next card in the north direction
        battle(cardAtNorth, x - 1, y, cardColor);
      }
    }

    // Battle the south card
    if (this.gameGrid.hasCard(y, x + 1)) {
      Card cardAtSouth = this.gameGrid.getGrid()[x + 1][y];
      if (card.compare(cardAtSouth, "south")) {
        cardAtSouth.reverseColor();
        this.numOfFlips += 1;
        this.gameGrid.getGrid()[x + 1][y] = cardAtSouth;
        // Recursively battle the next card in the south direction
        battle(cardAtSouth, x + 1, y, cardColor);
      }
    }

    // Reset player action based on the card color
    if (cardColor.equals("BLUE")) {
      playerAMoveInThisRound = false;
    } else {
      playerBMoveInThisRound = false;
    }
  }

  @Override
  public boolean isOver() {
    if (!isGameStart) {
      throw new IllegalStateException("The game has not started yet.");
    }
    return this.gameGrid.getCells() == 0;
  }

  @Override
  public IPlayer getWinner() {
    hasGameStartedOrOver(false);
    int red = 0;
    int blue = 0;
    for (int i = 0; i < gameGrid.getGrid().length; i += 1) {
      for (int j = 0; j < gameGrid.getGrid()[i].length; j += 1) {
        String cardColor = gameGrid.getGrid()[i][j].getColor();
        if (cardColor.equals("RED")) {
          red += 1;
        } else if (cardColor.equals("BLUE")) {
          blue += 1;
        }
      }
    }
    if (red > blue) {
      return playerA;
    } else if (red == blue) {
      return null;
    } else {
      return playerB;
    }
  }

  @Override
  public IPlayer getPlayerA() {
    hasGameStartedOrOver(false);
    return this.playerA;
  }

  @Override
  public IPlayer getPlayerB() {
    hasGameStartedOrOver(false);
    return this.playerB;
  }

  @Override
  public IGameGrid getGameGrid() {
    hasGameStartedOrOver(false);
    return this.gameGrid;
  }

  @Override
  public String getCurrentPlayer() {
    hasGameStartedOrOver(false);
    return this.currentTurn;
  }

  @Override
  public int countCardOnGrid(String color) {
    int red = 0;
    int blue = 0;
    for (int i = 0; i < gameGrid.getGrid().length; i += 1) {
      for (int j = 0; j < gameGrid.getGrid()[i].length; j += 1) {
        String cardColor = gameGrid.getGrid()[i][j].getColor();
        if (cardColor.equals("RED")) {
          red += 1;
        } else if (cardColor.equals("BLUE")) {
          blue += 1;
        }
      }
    }
    if (color.equals("RED")) {
      return red;
    } else {
      return blue;
    }
  }

  @Override
  public ThreeTriosGameModel copyGameModel() {
    ThreeTriosGameModel copy = new ThreeTriosGameModel(this.seed);
    copy.isGameStart = true;
    copy.playerAMoveInThisRound = false;
    copy.playerBMoveInThisRound = false;

    if (this.gameGrid != null) {
      copy.gameGrid = this.gameGrid.copy();
    }

    if (this.playerA != null) {
      copy.playerA = this.playerA.copy();
    }
    if (this.playerB != null) {
      copy.playerB = this.playerB.copy();
    }

    return copy;
  }

  private void hasGameStartedOrOver(boolean includeOver) {
    if (includeOver) {
      if (!isGameStart || isOver()) {
        throw new IllegalStateException("The game has not started yet or" +
                "the game is already over.");
      }
    } else {
      if (!isGameStart) {
        throw new IllegalStateException("The game has not started yet.");
      }
    }
  }

  @Override
  public Card[][] getCopyOfGameGrid() {
    hasGameStartedOrOver(false);
    ThreeTriosGameModel copy = new ThreeTriosGameModel(this);
    return copy.gameGrid.getGrid();
  }

  @Override
  public int getNumCardsCanFlip(int cardIdx, int x, int y, IPlayer player) {
    hasGameStartedOrOver(false);
    ThreeTriosGameModel aCopy = new ThreeTriosGameModel(this);
    aCopy.numOfFlips = 0;
    Player pCopy = new Player((Player) player);
    aCopy.playToGrid(cardIdx, x, y, player);
    Card card = aCopy.gameGrid.getGrid()[x][y];
    aCopy.battle(card, x, y, pCopy.getColor());
    return aCopy.numOfFlips;
  }

  @Override
  public int getAPlayerScore(IPlayer player) {
    hasGameStartedOrOver(false);
    int playerScore = 0;
    for (int i = 0; i < gameGrid.getGrid().length; i += 1) {
      for (int j = 0; j < gameGrid.getGrid()[i].length; j += 1) {
        String cardColor = gameGrid.getGrid()[i][j].getColor();
        if (cardColor.equals(player.getColor())) {
          playerScore++;
        }
      }
    }
    return playerScore;
  }

  @Override
  public void setCurrentPlayerMoved(boolean moved) {
    hasGameStartedOrOver(false);
    if (currentTurn.equals("RED")) {
      playerAMoveInThisRound = moved;
    } else if (currentTurn.equals("BLUE")) {
      playerBMoveInThisRound = moved;
    } else {
      throw new IllegalStateException("Invalid player turn.");
    }
  }

  @Override
  public boolean hasCurrentPlayerMoved() {
    hasGameStartedOrOver(false);
    if (currentTurn.equals("RED")) {
      return playerAMoveInThisRound;
    } else if (currentTurn.equals("BLUE")) {
      return playerBMoveInThisRound;
    } else {
      throw new IllegalStateException("Invalid player turn.");
    }
  }


  @Override
  public void switchToNextPlayer() {
    hasGameStartedOrOver(false);
    if (currentTurn.equals("RED")) {
      currentTurn = "BLUE";
      playerAMoveInThisRound = false;
    } else if (currentTurn.equals("BLUE")) {
      currentTurn = "RED";
      playerBMoveInThisRound = false;
    }
  }

  //had to add this to implement the other person's view
  @Override
  public List<Card> returnAllCards() {
    hasGameStartedOrOver(false);
    return this.originalCards;
  }
}
