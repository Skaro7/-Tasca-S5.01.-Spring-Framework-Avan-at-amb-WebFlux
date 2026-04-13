package cat.itacademy.blackjack.application;

import cat.itacademy.blackjack.domain.exception.GameNotFoundException;
import cat.itacademy.blackjack.domain.model.*;
import cat.itacademy.blackjack.domain.port.in.CreateGameUseCase;
import cat.itacademy.blackjack.domain.port.in.DeleteGameUseCase;
import cat.itacademy.blackjack.domain.port.in.GetGameUseCase;
import cat.itacademy.blackjack.domain.port.in.PlayGameUseCase;
import cat.itacademy.blackjack.infrastructure.exception.GlobalExceptionHandler;
import cat.itacademy.blackjack.infrastructure.web.controller.GameController;
import cat.itacademy.blackjack.infrastructure.web.mapper.GameWebMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock private CreateGameUseCase createGameUseCase;
    @Mock private GetGameUseCase getGameUseCase;
    @Mock private PlayGameUseCase playGameUseCase;
    @Mock private DeleteGameUseCase deleteGameUseCase;
    @Mock private GameWebMapper gameWebMapper;

    @InjectMocks
    private GameController gameController;

    private WebTestClient webTestClient;
    private Game testGame;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
                .bindToController(gameController)
                .controllerAdvice(new GlobalExceptionHandler())
                .build();

        testGame = Game.create(1L, "testPlayer");
        testGame.setId("test-id");
    }

    @Test
    void createGame_returns201() {
        when(createGameUseCase.createGame(anyString())).thenReturn(Mono.just(testGame));
        when(gameWebMapper.toResponse(any(Game.class))).thenReturn(
                new cat.itacademy.blackjack.infrastructure.web.dto.GameResponse(
                        "test-id", 1L, "testPlayer",
                        new cat.itacademy.blackjack.infrastructure.web.dto.HandResponse(java.util.List.of(), 0),
                        new cat.itacademy.blackjack.infrastructure.web.dto.HandResponse(java.util.List.of(), 0),
                        GameStatus.IN_PROGRESS,
                        testGame.getCreatedAt(), testGame.getUpdatedAt()
                )
        );

        webTestClient.post().uri("/game/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"playerName\": \"testPlayer\"}")
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo("test-id")
                .jsonPath("$.status").isEqualTo("IN_PROGRESS");
    }

    @Test
    void getGame_returns200() {
        when(getGameUseCase.getGame("test-id")).thenReturn(Mono.just(testGame));
        when(gameWebMapper.toResponse(any(Game.class))).thenReturn(
                new cat.itacademy.blackjack.infrastructure.web.dto.GameResponse(
                        "test-id", 1L, "testPlayer",
                        new cat.itacademy.blackjack.infrastructure.web.dto.HandResponse(java.util.List.of(), 0),
                        new cat.itacademy.blackjack.infrastructure.web.dto.HandResponse(java.util.List.of(), 0),
                        GameStatus.IN_PROGRESS,
                        testGame.getCreatedAt(), testGame.getUpdatedAt()
                )
        );

        webTestClient.get().uri("/game/test-id")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("test-id")
                .jsonPath("$.playerName").isEqualTo("testPlayer");
    }

    @Test
    void getGame_notFound_returns404() {
        when(getGameUseCase.getGame("bad-id"))
                .thenReturn(Mono.error(new GameNotFoundException("bad-id")));

        webTestClient.get().uri("/game/bad-id")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404);
    }

    @Test
    void play_returns200() {
        when(playGameUseCase.play(eq("test-id"), eq(PlayAction.HIT))).thenReturn(Mono.just(testGame));
        when(gameWebMapper.toResponse(any(Game.class))).thenReturn(
                new cat.itacademy.blackjack.infrastructure.web.dto.GameResponse(
                        "test-id", 1L, "testPlayer",
                        new cat.itacademy.blackjack.infrastructure.web.dto.HandResponse(java.util.List.of(), 0),
                        new cat.itacademy.blackjack.infrastructure.web.dto.HandResponse(java.util.List.of(), 0),
                        GameStatus.IN_PROGRESS,
                        testGame.getCreatedAt(), testGame.getUpdatedAt()
                )
        );

        webTestClient.post().uri("/game/test-id/play")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"action\": \"HIT\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("test-id");
    }

    @Test
    void deleteGame_returns204() {
        when(deleteGameUseCase.deleteGame("test-id")).thenReturn(Mono.empty());

        webTestClient.delete().uri("/game/test-id/delete")
                .exchange()
                .expectStatus().isNoContent();
    }
}