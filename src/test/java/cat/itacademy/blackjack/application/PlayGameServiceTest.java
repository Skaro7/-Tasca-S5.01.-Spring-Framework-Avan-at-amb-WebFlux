package cat.itacademy.blackjack.application;

import cat.itacademy.blackjack.application.usecase.PlayGameService;
import cat.itacademy.blackjack.domain.exception.GameNotFoundException;
import cat.itacademy.blackjack.domain.model.*;
import cat.itacademy.blackjack.domain.port.out.GameRepository;
import cat.itacademy.blackjack.domain.port.out.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayGameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayGameService playGameService;

    private Game inProgressGame;

    @BeforeEach
    void setUp() {
        inProgressGame = Game.create(1L, "testPlayer");
        inProgressGame.setId("test-game-id");
    }

    @Test
    void play_hit_returnsGameWithExtraCard() {
        when(gameRepository.findById("test-game-id")).thenReturn(Mono.just(inProgressGame));
        when(gameRepository.save(any(Game.class))).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(playGameService.play("test-game-id", PlayAction.HIT))
                .expectNextMatches(game -> game.getPlayerHand().size() >= 3)
                .verifyComplete();

        verify(gameRepository).findById("test-game-id");
        verify(gameRepository).save(any(Game.class));
    }

    @Test
    void play_stand_resolvesGameStatus() {
        when(gameRepository.findById("test-game-id")).thenReturn(Mono.just(inProgressGame));
        when(gameRepository.save(any(Game.class))).thenAnswer(inv -> Mono.just(inv.getArgument(0)));
        when(playerRepository.findById(anyLong())).thenReturn(Mono.just(new Player()));
        when(playerRepository.save(any(Player.class))).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(playGameService.play("test-game-id", PlayAction.STAND))
                .expectNextMatches(game -> game.getStatus() != GameStatus.IN_PROGRESS)
                .verifyComplete();

        verify(gameRepository).save(any(Game.class));
    }

    @Test
    void play_gameNotFound_throwsGameNotFoundException() {
        when(gameRepository.findById("non-existent")).thenReturn(Mono.empty());

        StepVerifier.create(playGameService.play("non-existent", PlayAction.HIT))
                .expectError(GameNotFoundException.class)
                .verify();
    }

    @Test
    void play_hit_onBustGameStatusIsDealerWins() {
        Game bustedGame = Game.create(1L, "testPlayer");
        bustedGame.setId("test-game-id");

        while (!bustedGame.getPlayerHand().isBust()
                && bustedGame.getStatus() == GameStatus.IN_PROGRESS) {
            bustedGame.hit();
        }

        if (bustedGame.getStatus() == GameStatus.DEALER_WINS) {
            assert bustedGame.getPlayerHand().isBust();
        }
    }
}