package cat.itacademy.blackjack.domain.port.in;

import cat.itacademy.blackjack.domain.model.Player;
import reactor.core.publisher.Flux;

public interface GetRankingUseCase {
    Flux<Player> getRanking();
}
