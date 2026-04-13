package cat.itacademy.blackjack.infrastructure.persistence.mongo.repository;

import cat.itacademy.blackjack.infrastructure.persistence.mongo.document.GameDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SpringDataGameRepository extends ReactiveMongoRepository<GameDocument, String> {
}
