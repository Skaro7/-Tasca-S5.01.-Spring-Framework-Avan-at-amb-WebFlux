package cat.itacademy.blackjack.application.usecase;

import cat.itacademy.blackjack.domain.exception.GameNotFoundException;
import cat.itacademy.blackjack.domain.model.Game;
import cat.itacademy.blackjack.domain.port.in.GetGameUseCase;
import cat.itacademy.blackjack.domain.port.out.GameRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetGameService implements GetGameUseCase {

    private final GameRepository gameRepository;

    public GetGameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Mono<Game> getGame(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException(gameId)));
    }
}
