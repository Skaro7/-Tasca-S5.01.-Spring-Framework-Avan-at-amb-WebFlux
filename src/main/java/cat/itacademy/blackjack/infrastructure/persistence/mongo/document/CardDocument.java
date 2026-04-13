package cat.itacademy.blackjack.infrastructure.persistence.mongo.document;

import cat.itacademy.blackjack.domain.model.Rank;
import cat.itacademy.blackjack.domain.model.Suit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDocument {
    private Rank rank;
    private Suit suit;
}
