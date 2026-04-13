package cat.itacademy.blackjack.infrastructure.persistence.mongo.repository;

import cat.itacademy.blackjack.domain.model.Game;
import cat.itacademy.blackjack.domain.port.out.GameRepository;
import cat.itacademy.blackjack.infrastructure.persistence.mongo.document.GameDocumentMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Output adapter — implements domain port GameRepository using MongoDB.
 */
@Repository
public class MongoGameRepositoryAdapter implements GameRepository {

    private final SpringDataGameRepository springRepo;
    private final GameDocumentMapper mapper;

    public MongoGameRepositoryAdapter(SpringDataGameRepository springRepo,
                                      GameDocumentMapper mapper) {
        this.springRepo = springRepo;
        this.mapper     = mapper;
    }

    @Override
    public Mono<Game> save(Game game) {
        return springRepo.save(mapper.toDocument(game))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Game> findById(String id) {
        return springRepo.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return springRepo.deleteById(id);
    }
}