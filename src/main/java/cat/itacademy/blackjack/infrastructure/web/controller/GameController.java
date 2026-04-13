package cat.itacademy.blackjack.infrastructure.web.controller;

import cat.itacademy.blackjack.domain.port.in.CreateGameUseCase;
import cat.itacademy.blackjack.domain.port.in.DeleteGameUseCase;
import cat.itacademy.blackjack.domain.port.in.GetGameUseCase;
import cat.itacademy.blackjack.domain.port.in.PlayGameUseCase;
import cat.itacademy.blackjack.infrastructure.web.dto.CreateGameRequest;
import cat.itacademy.blackjack.infrastructure.web.dto.GameResponse;
import cat.itacademy.blackjack.infrastructure.web.dto.PlayRequest;
import cat.itacademy.blackjack.infrastructure.web.mapper.GameWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
@Tag(name = "Game", description = "Blackjack game management")
public class GameController {

    private final CreateGameUseCase createGameUseCase;
    private final GetGameUseCase getGameUseCase;
    private final PlayGameUseCase playGameUseCase;
    private final DeleteGameUseCase deleteGameUseCase;
    private final GameWebMapper mapper;

    public GameController(CreateGameUseCase createGameUseCase,
                          GetGameUseCase getGameUseCase,
                          PlayGameUseCase playGameUseCase,
                          DeleteGameUseCase deleteGameUseCase,
                          GameWebMapper mapper) {
        this.createGameUseCase = createGameUseCase;
        this.getGameUseCase    = getGameUseCase;
        this.playGameUseCase   = playGameUseCase;
        this.deleteGameUseCase = deleteGameUseCase;
        this.mapper            = mapper;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new Blackjack game")
    public Mono<GameResponse> createGame(@Valid @RequestBody CreateGameRequest request) {
        return createGameUseCase.createGame(request.playerName())
                .map(mapper::toResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get game details by ID")
    public Mono<GameResponse> getGame(@PathVariable String id) {
        return getGameUseCase.getGame(id)
                .map(mapper::toResponse);
    }

    @PostMapping("/{id}/play")
    @Operation(summary = "Perform a play action (HIT or STAND)")
    public Mono<GameResponse> play(@PathVariable String id,
                                   @Valid @RequestBody PlayRequest request) {
        return playGameUseCase.play(id, request.action())
                .map(mapper::toResponse);
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a game by ID")
    public Mono<Void> deleteGame(@PathVariable String id) {
        return deleteGameUseCase.deleteGame(id);
    }
}
