package cat.itacademy.blackjack.domain.port.in;

import cat.itacademy.blackjack.domain.model.Game;
import cat.itacademy.blackjack.domain.model.PlayAction;
import reactor.core.publisher.Mono;

public interface PlayGameUseCase {
    Mono<Game> play(String gameId, PlayAction action);
}
