package cat.itacademy.blackjack.application.usecase;

import cat.itacademy.blackjack.domain.model.Player;
import cat.itacademy.blackjack.domain.port.in.GetRankingUseCase;
import cat.itacademy.blackjack.domain.port.out.PlayerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class GetRankingService implements GetRankingUseCase {

    private final PlayerRepository playerRepository;

    public GetRankingService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Flux<Player> getRanking() {
        return playerRepository.findAllOrderByWinRateDesc();
    }
}
