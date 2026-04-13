package cat.itacademy.blackjack.application.usecase;

import cat.itacademy.blackjack.domain.model.Game;
import cat.itacademy.blackjack.domain.model.Player;
import cat.itacademy.blackjack.domain.port.in.CreateGameUseCase;
import cat.itacademy.blackjack.domain.port.out.GameRepository;
import cat.itacademy.blackjack.domain.port.out.PlayerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateGameService implements CreateGameUseCase {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    public CreateGameService(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository   = gameRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Mono<Game> createGame(String playerName) {
        // Find existing player or create a new one
        return playerRepository.findByUsername(playerName)
                .switchIfEmpty(Mono.defer(() ->
                        playerRepository.save(Player.create(playerName))))
                .flatMap(player -> {
                    Game game = Game.create(player.getId(), player.getUsername());
                    return gameRepository.save(game);
                });
    }
}
