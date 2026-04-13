package cat.itacademy.blackjack.application.usecase;

import cat.itacademy.blackjack.domain.exception.PlayerNotFoundException;
import cat.itacademy.blackjack.domain.model.Player;
import cat.itacademy.blackjack.domain.port.in.UpdatePlayerNameUseCase;
import cat.itacademy.blackjack.domain.port.out.PlayerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdatePlayerNameService implements UpdatePlayerNameUseCase {

    private final PlayerRepository playerRepository;

    public UpdatePlayerNameService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Mono<Player> updateName(Long playerId, String newName) {
        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException(playerId)))
                .flatMap(player -> {
                    player.rename(newName);
                    return playerRepository.save(player);
                });
    }
}
