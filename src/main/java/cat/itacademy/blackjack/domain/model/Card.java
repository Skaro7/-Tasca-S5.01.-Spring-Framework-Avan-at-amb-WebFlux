package cat.itacademy.blackjack.domain.model;

import java.util.Objects;

/**
 * Value Object — immutable, equality by value.
 */
public final class Card {

    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = Objects.requireNonNull(rank, "Rank must not be null");
        this.suit = Objects.requireNonNull(suit, "Suit must not be null");
    }

    public Rank getRank() { return rank; }
    public Suit getSuit() { return suit; }

    public int getValue() { return rank.getValue(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card card)) return false;
        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode() { return Objects.hash(rank, suit); }

    @Override
    public String toString() { return rank + " of " + suit; }
}
