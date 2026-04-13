package cat.itacademy.blackjack.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateGameRequest(
        @NotBlank(message = "Player name must not be blank")
        String playerName
) {}
