package cat.itacademy.blackjack.infrastructure.web.controller;

import cat.itacademy.blackjack.domain.port.in.GetRankingUseCase;
import cat.itacademy.blackjack.domain.port.in.UpdatePlayerNameUseCase;
import cat.itacademy.blackjack.infrastructure.web.dto.PlayerResponse;
import cat.itacademy.blackjack.infrastructure.web.dto.UpdatePlayerNameRequest;
import cat.itacademy.blackjack.infrastructure.web.mapper.PlayerWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Tag(name = "Player", description = "Player management and ranking")
public class PlayerController {

    private final GetRankingUseCase getRankingUseCase;
    private final UpdatePlayerNameUseCase updatePlayerNameUseCase;
    private final PlayerWebMapper mapper;

    public PlayerController(GetRankingUseCase getRankingUseCase,
                            UpdatePlayerNameUseCase updatePlayerNameUseCase,
                            PlayerWebMapper mapper) {
        this.getRankingUseCase       = getRankingUseCase;
        this.updatePlayerNameUseCase = updatePlayerNameUseCase;
        this.mapper                  = mapper;
    }

    @GetMapping("/ranking")
    @Operation(summary = "Get player ranking ordered by win rate")
    public Flux<PlayerResponse> getRanking() {
        return getRankingUseCase.getRanking()
                .map(mapper::toResponse);
    }

    @PutMapping("/player/{playerId}")
    @Operation(summary = "Update player username")
    public Mono<PlayerResponse> updatePlayerName(@PathVariable Long playerId,
                                                 @Valid @RequestBody UpdatePlayerNameRequest request) {
        return updatePlayerNameUseCase.updateName(playerId, request.username())
                .map(mapper::toResponse);
    }
}
