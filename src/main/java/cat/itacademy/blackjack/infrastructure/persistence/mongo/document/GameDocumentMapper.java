package cat.itacademy.blackjack.infrastructure.persistence.mongo.document;

import cat.itacademy.blackjack.domain.model.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameDocumentMapper {

    public GameDocument toDocument(Game game) {
        GameDocument doc = new GameDocument();
        doc.setId(game.getId());
        doc.setPlayerId(game.getPlayerId());
        doc.setPlayerName(game.getPlayerName());
        doc.setPlayerHand(toHandDocument(game.getPlayerHand()));
        doc.setDealerHand(toHandDocument(game.getDealerHand()));
        doc.setDeckCards(game.getDeck().getCards().stream()
                .map(c -> new CardDocument(c.getRank(), c.getSuit()))
                .toList());
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

    private Deck toDeck(List<CardDocument> cards) {
        if (cards == null || cards.isEmpty()) return new Deck();
        return new Deck(cards.stream()
                .map(c -> new Card(c.getRank(), c.getSuit()))
                .toList());
    }
}
