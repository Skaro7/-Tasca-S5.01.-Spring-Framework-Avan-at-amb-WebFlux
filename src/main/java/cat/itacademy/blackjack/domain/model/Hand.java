package cat.itacademy.blackjack.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class Hand {

    private final List<Card> cards;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    private Hand(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public Hand addCard(Card card) {
        List<Card> newCards = new ArrayList<>(this.cards);
        newCards.add(card);
        return new Hand(newCards);
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public int getScore() {
        int score = 0;
        int aces = 0;
        for (Card card : cards) {
            score += card.getValue();
            if (card.getRank() == Rank.ACE) aces++;
        }
        while (score > 21 && aces > 0) {
            score -= 10;
            aces--;
        }
        return score;
    }

    public boolean isBust() { return getScore() > 21; }

    public boolean isBlackjack() {
        return cards.size() == 2 && getScore() == 21;
    }

    public int size() { return cards.size(); }

    @Override
    public String toString() { return cards + " (score=" + getScore() + ")"; }
}