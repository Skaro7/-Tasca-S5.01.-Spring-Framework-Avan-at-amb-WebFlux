package cat.itacademy.blackjack.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class Deck {

    private final List<Card> cards;

    public Deck() {
        List<Card> deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                deck.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(deck);
        this.cards = Collections.unmodifiableList(deck);
    }

    private Deck(List<Card> cards) {
        this.cards = Collections.unmodifiableList(new ArrayList<>(cards));
    }

    public DrawResult draw() {
        if (cards.isEmpty()) throw new IllegalStateException("Deck is empty");
        Card drawn = cards.get(0);
        Deck remaining = new Deck(cards.subList(1, cards.size()));
        return new DrawResult(drawn, remaining);
    }

    public boolean isEmpty() { return cards.isEmpty(); }
    public int size() { return cards.size(); }

    public record DrawResult(Card card, Deck remainingDeck) {}
}