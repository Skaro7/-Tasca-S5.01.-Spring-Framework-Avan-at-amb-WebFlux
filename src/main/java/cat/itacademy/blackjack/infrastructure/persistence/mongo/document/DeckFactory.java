package cat.itacademy.blackjack.infrastructure.persistence.mongo.document;

import cat.itacademy.blackjack.domain.model.Card;
import cat.itacademy.blackjack.domain.model.Deck;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;


public class DeckFactory {

    private DeckFactory() {}

    public static Deck fromCards(List<Card> cards) {
        try {
            Constructor<Deck> constructor = Deck.class.getDeclaredConstructor(List.class);
            constructor.setAccessible(true);
            return constructor.newInstance(Collections.unmodifiableList(cards));
        } catch (Exception e) {
            throw new IllegalStateException("Cannot reconstruct Deck from saved cards", e);
        }
    }
}