package cat.itacademy.blackjack.infrastructure.web.mapper;

import cat.itacademy.blackjack.domain.model.Game;
import cat.itacademy.blackjack.domain.model.Hand;
import cat.itacademy.blackjack.infrastructure.web.dto.CardResponse;
import cat.itacademy.blackjack.infrastructure.web.dto.GameResponse;
import cat.itacademy.blackjack.infrastructure.web.dto.HandResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameWebMapper {

    public GameResponse toResponse(Game game) {
        return new GameResponse(
                game.getId(),
                game.getPlayerId(),
                game.getPlayerName(),
                toHandResponse(game.getPlayerHand()),
                toHandResponse(game.getDealerHand()),
                game.getStatus(),
                game.getCreatedAt(),
                game.getUpdatedAt()
        );
    }

    private HandResponse toHandResponse(Hand hand) {
        List<CardResponse> cards = hand.getCards().stream()
                .map(c -> new CardResponse(c.getRank(), c.getSuit(), c.getValue()))
                .toList();
        return new HandResponse(cards, hand.getScore());
    }
}
