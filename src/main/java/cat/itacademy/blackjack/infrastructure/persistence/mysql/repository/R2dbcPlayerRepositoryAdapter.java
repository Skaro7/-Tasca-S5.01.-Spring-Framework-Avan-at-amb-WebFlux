package cat.itacademy.blackjack.infrastructure.persistence.mysql.repository;

import cat.itacademy.blackjack.domain.model.Player;
import cat.itacademy.blackjack.domain.port.out.PlayerRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Output adapter — implements domain port PlayerRepository using MySQL/R2DBC.
 */
@Repository
public class R2dbcPlayerRepositoryAdapter implements PlayerRepository {

    private final SpringDataPlayerRepository springRepo;
    private final PlayerEntityMapper mapper;

    public R2dbcPlayerRepositoryAdapter(SpringDataPlayerRepository springRepo,
                                        PlayerEntityMapper mapper) {
        this.springRepo = springRepo;
        this.mapper     = mapper;
    }

    @Override
    public Mono<Player> save(Player player) {
        return springRepo.save(mapper.toEntity(player))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Player> findById(Long id) {
        return springRepo.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Player> findByUsername(String username) {
        return springRepo.findByUsername(username)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Player> findAllOrderByWinRateDesc() {
        return springRepo.findAllOrderByWinRateDesc()
                .map(mapper::toDomain);
    }
}