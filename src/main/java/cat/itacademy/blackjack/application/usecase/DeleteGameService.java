package cat.itacademy.blackjack.application.usecase;

import cat.itacademy.blackjack.domain.exception.GameNotFoundException;
import cat.itacademy.blackjack.domain.port.in.DeleteGameUseCase;
import cat.itacademy.blackjack.domain.port.out.GameRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteGameService implements DeleteGameUseCase {

    private final GameRepository gameRepository;

    public DeleteGameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Mono<Void> deleteGame(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException(gameId)))
                .flatMap(game -> gameRepository.deleteById(game.getId()));
    }
}
