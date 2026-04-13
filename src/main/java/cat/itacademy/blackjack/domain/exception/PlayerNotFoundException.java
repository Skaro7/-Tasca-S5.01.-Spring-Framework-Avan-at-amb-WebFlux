package cat.itacademy.blackjack.domain.exception;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(Long playerId) {
        super("Player not found: " + playerId);
    }
}