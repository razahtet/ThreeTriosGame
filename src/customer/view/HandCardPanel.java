package customer.view;

import java.awt.GridLayout;
import java.awt.Component;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;

import customer.controller.Controller;
import customer.gamefeatures.BattleType;
import customer.gamefeatures.Card;
import customer.gamefeatures.ModType;

/**
 * JPanel that displays the player's hand cards.
 */
public class HandCardPanel extends JPanel {
  private List<Card> cards;
  private final String cardColor;
  private final int cardLength;
  private int cardWidth; // Removed final modifier
  private int selectedCardIndex = -1;
  private final List<CardPanel> cardPanels = new ArrayList<>();
  private Controller controller;
  private final BattleType battleType;
  private final ModType modType;

  /**
   * Constructor for HandCardPanel class.
   *
   * @param cards      Card list.
   * @param cardColor  Player color.
   * @param cardLength Card length.
   * @param gridWidth  Grid width.
   */
  public HandCardPanel(List<Card> cards, String cardColor, int cardLength, int gridWidth,
                       BattleType battleType, ModType modType) {
    this.cards = new ArrayList<>(cards);
    this.cardColor = cardColor;
    this.cardLength = cardLength;
    this.cardWidth = gridWidth / Math.max(cards.size(), 1);
    this.battleType = battleType;
    this.modType = modType;
    setLayout(new GridLayout(0, 1, 5, 5)); // Dynamic row count, 1 column, spacing 5px
    initializeHandCards();
  }

  /**
   * Set the Controller.
   *
   * @param controller Controller to set.
   */
  public void setController(Controller controller) {
    this.controller = controller;

    // Register listeners for each card button
    for (Component component : this.getComponents()) {
      if (component instanceof JButton) {
        JButton cardButton = (JButton) component;
        cardButton.addActionListener(e -> {
          if (controller != null) {
            // Assuming the card button's action command stores the card index
            int cardIndex = Integer.parseInt(cardButton.getActionCommand());
            controller.onCardSelected(cardIndex, cardColor);
          }
        });
      }
    }
  }


  /**
   * Initialize the hand cards in the panel.
   */
  private void initializeHandCards() {
    removeAll();
    cardPanels.clear();
    selectedCardIndex = -1; // Reset selection state

    for (int i = 0; i < cards.size(); i++) {
      final int cardIndex = i;
      Card card = cards.get(i);
      card.setBattleType(battleType);
      card.setModType(modType);
      CardPanel cardPanel = new CardPanel(
              cardLength,
              cardWidth,
              cardColor,
              card.toString(),
              ""
      );
      cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

      // Add mouse listener to handle card click events
      cardPanel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          if (controller != null && controller.isHumanControlled()) {
            handleCardSelection(cardIndex);
          }
        }
      });

      cardPanels.add(cardPanel);
      add(cardPanel);
    }

    revalidate();
    repaint();
  }

  /**
   * Handle the card selection logic.
   *
   * @param cardIndex The index of the card being selected.
   */
  private void handleCardSelection(int cardIndex) {
    if (selectedCardIndex == cardIndex) {
      // Deselect if already selected
      setCardSelected(cardPanels.get(cardIndex), false);
      selectedCardIndex = -1;
      controller.onCardSelected(cardIndex, cardColor);
    } else {
      // Deselect previously selected card if any
      if (selectedCardIndex != -1) {
        setCardSelected(cardPanels.get(selectedCardIndex), false);
      }
      // Select new card
      // Notify the customer.provider.controller about the card selection
      if (controller != null && controller.isHumanControlled()) {
        if (controller.onCardSelected(cardIndex, cardColor)) {
          setCardSelected(cardPanels.get(cardIndex), true);
          selectedCardIndex = cardIndex;
          controller.hintTime(false, false);
        }
      }
    }
    if (selectedCardIndex == -1) {
      if (controller != null && controller.isHumanControlled()) {
        controller.hintTime(false, true);
      }
    }
  }

  /**
   * Set the card selection state, which changes the border.
   *
   * @param cardPanel  The panel of the card.
   * @param isSelected Whether the card is selected.
   */
  private void setCardSelected(CardPanel cardPanel, boolean isSelected) {
    if (isSelected) {
      cardPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
    } else {
      cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }
  }

  /**
   * Update the hand cards with the new list of cards.
   *
   * @param newCards The new list of cards to update.
   */
  public void updateHandCards(List<Card> newCards) {
    this.cards = new ArrayList<>(newCards);
    this.cardWidth = Math.max(1, this.getWidth() / Math.max(cards.size(), 1));
    setLayout(new GridLayout(cards.size(), 1, 5, 5));
    initializeHandCards();
  }

  /**
   * Get the index of the currently selected card.
   *
   * @return Selected card index, or -1 if none selected.
   */
  public int getSelectedCardIndex() {
    return selectedCardIndex;
  }

  /**
   * Get the currently selected card.
   *
   * @return The selected Card, or null if none selected.
   */
  public Card getSelectedCard() {
    if (selectedCardIndex != -1 && selectedCardIndex < cards.size()) {
      return cards.get(selectedCardIndex);
    }
    return null;
  }
}
