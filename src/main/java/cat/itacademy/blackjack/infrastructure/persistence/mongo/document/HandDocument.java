package cat.itacademy.blackjack.infrastructure.persistence.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandDocument {
    private List<CardDocument> cards;
}
