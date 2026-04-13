package cat.itacademy.blackjack.infrastructure.web.dto;

import java.time.LocalDateTime;

public record PlayerResponse(
        Long id,
        String username,
        int wins,
        int losses,
        double winRate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
