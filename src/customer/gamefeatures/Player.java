package customer.gamefeatures;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents a player in the game.
 * Contains the color represent the player and
 * the list of hand card that this player have.
 */
public class Player implements IPlayer {

  private final String color;
  private List<Card> hand;

  /**
   * Constructor method of the Player class.
   *
   * @param color The color represent the player.
   */
  public Player(String color) {
    this.color = color;
    this.hand = new ArrayList<>();
  }

  /**
   * Constructor method that makes a copy of another Player.
   *
   * @param another The Player that is getting copied.
   */
  public Player(Player another) {
    this.color = another.color;
    this.hand = new ArrayList<>(another.hand);
  }

  @Override
  public String getColor() {
    return this.color;
  }

  @Override
  public void addCardsToHand(Card card) {
    hand.add(card);
  }

  @Override
  public Card getCardFromHand(int index) {
    if (index < 0 || index > hand.size() - 1) {
      throw new IllegalArgumentException(
              "The given hand card index should between 0 and the hand card size for now.");
    }
    if (this.hand.isEmpty()) {
      throw new IllegalStateException("There is no card left");
    }
    // get the card from the hand using remove();
    Card card = hand.get(index);
    return card;
  }

  @Override
  public List<Card> getHand() {
    return this.hand;
  }

  @Override
  public IPlayer copy() {
    Player copy = new Player(getColor());
    List<Card> copyHand = new ArrayList<>();
    for (Card card : this.hand) {
      copyHand.add(card.copy());
    }
    copy.hand = copyHand;
    return copy;
  }


}
