package cat.itacademy.blackjack.domain.port.out;

import cat.itacademy.blackjack.domain.model.Player;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlayerRepository {
    Mono<Player> save(Player player);
    Mono<Player> findById(Long id);
    Mono<Player> findByUsername(String username);
    Flux<Player> findAllOrderByWinRateDesc();
}