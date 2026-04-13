package cat.itacademy.blackjack.infrastructure.web.dto;

import cat.itacademy.blackjack.domain.model.PlayAction;
import jakarta.validation.constraints.NotNull;

public record PlayRequest(
        @NotNull(message = "Action must not be null")
        PlayAction action
) {}
