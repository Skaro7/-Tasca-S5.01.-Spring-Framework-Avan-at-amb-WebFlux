package cat.itacademy.blackjack.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdatePlayerNameRequest(
        @NotBlank(message = "Username must not be blank")
        String username
) {}
