package cat.itacademy.blackjack.infrastructure.persistence.mysql.repository;

import cat.itacademy.blackjack.infrastructure.persistence.mysql.entity.PlayerEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SpringDataPlayerRepository extends ReactiveCrudRepository<PlayerEntity, Long> {

    Mono<PlayerEntity> findByUsername(String username);

    @Query("SELECT * FROM players ORDER BY win_rate DESC")
    Flux<PlayerEntity> findAllOrderByWinRateDesc();
}
