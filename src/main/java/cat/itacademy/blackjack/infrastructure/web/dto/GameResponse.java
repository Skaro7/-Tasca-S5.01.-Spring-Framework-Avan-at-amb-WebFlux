package cat.itacademy.blackjack.infrastructure.web.dto;

import cat.itacademy.blackjack.domain.model.GameStatus;

import java.time.LocalDateTime;

public record GameResponse(
        String id,
        Long playerId,
        String playerName,
        HandResponse playerHand,
        HandResponse dealerHand,
        GameStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
