package cat.itacademy.blackjack.domain.port.in;

import cat.itacademy.blackjack.domain.model.Player;
import reactor.core.publisher.Mono;

public interface UpdatePlayerNameUseCase {
    Mono<Player> updateName(Long playerId, String newName);
}
