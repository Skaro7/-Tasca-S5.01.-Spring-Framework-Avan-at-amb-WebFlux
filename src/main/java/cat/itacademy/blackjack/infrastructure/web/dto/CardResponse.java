package cat.itacademy.blackjack.infrastructure.web.dto;

import cat.itacademy.blackjack.domain.model.Rank;
import cat.itacademy.blackjack.domain.model.Suit;

public record CardResponse(Rank rank, Suit suit, int value) {}
