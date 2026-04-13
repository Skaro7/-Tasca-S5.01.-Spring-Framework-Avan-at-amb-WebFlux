package cat.itacademy.blackjack.infrastructure.persistence.mongo.document;

import cat.itacademy.blackjack.domain.model.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Manual mapper between Game (domain) and GameDocument (MongoDB).
 * Keeps the domain free of persistence annotations.
 */
@Component
public class GameDocumentMapper {

    public GameDocument toDocument(Game game) {
        GameDocument doc = new GameDocument();
        doc.setId(game.getId());
        doc.setPlayerId(game.getPlayerId());
        doc.setPlayerName(game.getPlayerName());
        doc.setPlayerHand(toHandDocument(game.getPlayerHand()));
        doc.setDealerHand(toHandDocument(game.getDealerHand()));
        doc.setDeckCards(toDeckCards(game.getDeck()));
        doc.setStatus(game.getStatus());
        doc.setCreatedAt(game.getCreatedAt());
        doc.setUpdatedAt(game.getUpdatedAt());
        return doc;
    }

    public Game toDomain(GameDocument doc) {
        Game game = new Game();
        game.setId(doc.getId());
        game.setPlayerId(doc.getPlayerId());
        game.setPlayerName(doc.getPlayerName());
        game.setPlayerHand(toHand(doc.getPlayerHand()));
        game.setDealerHand(toHand(doc.getDealerHand()));
        game.setDeck(toDeck(doc.getDeckCards()));
        game.setStatus(doc.getStatus());
        game.setCreatedAt(doc.getCreatedAt());
        game.setUpdatedAt(doc.getUpdatedAt());
        return game;
    }

    private HandDocument toHandDocument(Hand hand) {
        List<CardDocument> cards = hand.getCards().stream()
                .map(c -> new CardDocument(c.getRank(), c.getSuit()))
                .toList();
        return new HandDocument(cards);
    }

    private Hand toHand(HandDocument doc) {
        Hand hand = new Hand();
        for (CardDocument c : doc.getCards()) {
            hand = hand.addCard(new Card(c.getRank(), c.getSuit()));
        }
        return hand;
    }

    private List<CardDocument> toDeckCards(Deck deck) {
        // We need access to the cards list — Deck exposes them via draw()
        // so we serialise by drawing all cards into a list
        List<CardDocument> result = new java.util.ArrayList<>();
        Deck current = deck;
        while (!current.isEmpty()) {
            Deck.DrawResult dr = current.draw();
            result.add(new CardDocument(dr.card().getRank(), dr.card().getSuit()));
            current = dr.remainingDeck();
        }
        return result;
    }

    private Deck toDeck(List<CardDocument> cards) {
        // Reconstruct deck preserving order (no reshuffle)
        if (cards == null || cards.isEmpty()) return new Deck();
        // Build via reflection-free approach: create ordered deck subclass
        return DeckFactory.fromCards(cards.stream()
                .map(c -> new Card(c.getRank(), c.getSuit()))
                .toList());
    }
}