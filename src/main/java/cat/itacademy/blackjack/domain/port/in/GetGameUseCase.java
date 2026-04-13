package cat.itacademy.blackjack.domain.port.in;

import cat.itacademy.blackjack.domain.model.Game;
import reactor.core.publisher.Mono;

public interface GetGameUseCase {
    Mono<Game> getGame(String gameId);
}
