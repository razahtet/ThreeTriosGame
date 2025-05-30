package customer.gamefeatures;

import customer.variantdecorator.FallenAceDecorator;
import customer.variantdecorator.IVariant;
import customer.variantdecorator.NormalBattleDecorator;
import customer.variantdecorator.ReverseBattleDecorator;
import customer.variantdecorator.SameDecorator;

/**
 * Class for the card be used in this game.
 */
public class GameCard implements Card {

  private final String cardName;
  private String cardColor;
  private final Direction north;
  private final Direction south;
  private final Direction east;
  private final Direction west;
  private BattleType battleType;
  private ModType modType;

  /**
   * Constructor for GameCard.
   *
   * @param name      Name of the card
   * @param cardColor Color of the card.
   * @param north     ATK value at north.
   * @param south     ATK value at south.
   * @param east      ATK value at east.
   * @param west      ATK value at west.
   */
  public GameCard(String name, String cardColor,
                  int north, int south, int east, int west) {
    this.cardName = name;
    this.cardColor = cardColor;
    this.north = new Direction(north);
    this.south = new Direction(south);
    this.east = new Direction(east);
    this.west = new Direction(west);
  }

  /**
   * Constructor for GameCard that copies another GameCard.
   *
   * @param another Another GameCard object.
   */
  public GameCard(GameCard another) {
    this.cardName = another.cardName;
    this.cardColor = another.cardColor;
    this.north = new Direction(another.north);
    this.south = new Direction(another.south);
    this.east = new Direction(another.east);
    this.west = new Direction(another.west);
    this.battleType = another.battleType;
  }

  @Override
  public void setBattleType(BattleType battleType) {
    this.battleType = battleType;
  }

  @Override
  public void setModType(ModType modType) {
    this.modType = modType;
  }

  @Override
  public int getNum(String direction) {
    switch (direction) {
      case "north":
        return this.north.getCardNum();
      case "south":
        return this.south.getCardNum();
      case "east":
        return this.east.getCardNum();
      case "west":
        return this.west.getCardNum();
      default:
        throw new IllegalArgumentException(
                "The direction is should be one of north/south/east/west.");
    }
  }

  @Override
  public String getColor() {
    return this.cardColor;
  }

  @Override
  public void reverseColor() {
    if (this.cardColor.equals("RED")) {
      this.cardColor = "BLUE";
    } else {
      this.cardColor = "RED";
    }
  }

  @Override
  public boolean compare(Card card, String direction) {
    // check whether the given card has the same color as this card.
    if (this.cardColor.equals(card.getColor())) {
      return false;
    }
    return battlingProcess(card, direction);
  }

  private boolean battlingProcess(Card card, String direction) {
    switch (direction) {
      case "north":
        return compareTheNums(this.north.getCardNum(), card.getNum("south"));
      case "south":
        return compareTheNums(this.south.getCardNum(), card.getNum("north"));
      case "east":
        return compareTheNums(this.east.getCardNum(), card.getNum("west"));
      case "west":
        return compareTheNums(this.west.getCardNum(), card.getNum("east"));
      default:
        throw new IllegalArgumentException(
                "The direction is should be one of north/south/east/west.");
    }
  }

  private boolean compareTheNums(int num1, int num2) {
    IVariant battleDec = new NormalBattleDecorator(num1, num2);

    // battle type part
    if (battleType == BattleType.REVERSE) {
      battleDec = new ReverseBattleDecorator(battleDec);
    } else if (battleType == BattleType.ACE) {
      battleDec = new FallenAceDecorator(battleDec, num1, num2);
    } else if (battleType == BattleType.REVERSEANDACE) {
      battleDec = new ReverseBattleDecorator(new FallenAceDecorator(battleDec, num1, num2));
    }

    // modification add-on part
    if (modType == ModType.SAME) {
      battleDec = new SameDecorator(battleDec, num1, num2);
    }

    return battleDec.compareCard();
  }

  @Override
  public String getName() {
    return this.cardName;
  }

  @Override
  public String toString() {
    String cardInfo = this.getName();
    cardInfo = cardInfo + " " + this.north
            +
            " " + this.south
            +
            " " + this.east
            +
            " " + this.west;
    return cardInfo;
  }

  @Override
  public int getChanceToFlip(String direction) {
    return 10 - this.getNum(direction);
  }

  @Override
  public Card copy() {
    Card copy = new GameCard(getName(), getColor(),
            getNum("north"), getNum("south"),
            getNum("east"), getNum("west"));
    return copy;
  }

}
