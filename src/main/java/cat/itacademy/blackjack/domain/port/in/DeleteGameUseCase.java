package cat.itacademy.blackjack.domain.port.in;

import reactor.core.publisher.Mono;

public interface DeleteGameUseCase {
    Mono<Void> deleteGame(String gameId);
}
