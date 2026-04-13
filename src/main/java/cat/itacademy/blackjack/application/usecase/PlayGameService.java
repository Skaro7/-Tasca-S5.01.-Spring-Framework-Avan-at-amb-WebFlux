package cat.itacademy.blackjack.application.usecase;

import cat.itacademy.blackjack.domain.exception.GameNotFoundException;
import cat.itacademy.blackjack.domain.model.Game;
import cat.itacademy.blackjack.domain.model.GameStatus;
import cat.itacademy.blackjack.domain.model.PlayAction;
import cat.itacademy.blackjack.domain.port.in.PlayGameUseCase;
import cat.itacademy.blackjack.domain.port.out.GameRepository;
import cat.itacademy.blackjack.domain.port.out.PlayerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PlayGameService implements PlayGameUseCase {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    public PlayGameService(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository   = gameRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Mono<Game> play(String gameId, PlayAction action) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException(gameId)))
                .flatMap(game -> {
                    if (action == PlayAction.HIT) {
                        game.hit();
                    } else {
                        game.stand();
                    }
                    return gameRepository.save(game)
                            .flatMap(saved -> updatePlayerStatsIfFinished(saved)
                                    .thenReturn(saved));
                });
    }

    private Mono<Void> updatePlayerStatsIfFinished(Game game) {
        if (game.getStatus() == GameStatus.IN_PROGRESS) {
            return Mono.empty();
        }
        return playerRepository.findById(game.getPlayerId())
                .flatMap(player -> {
                    if (game.getStatus() == GameStatus.PLAYER_WINS) {
                        player.recordWin();
                    } else if (game.getStatus() == GameStatus.DEALER_WINS) {
                        player.recordLoss();
                    }
                    // PUSH: no win, no loss
                    return playerRepository.save(player);
                })
                .then();
    }
}
