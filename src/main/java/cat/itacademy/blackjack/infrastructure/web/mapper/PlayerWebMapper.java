package cat.itacademy.blackjack.infrastructure.web.mapper;

import cat.itacademy.blackjack.domain.model.Player;
import cat.itacademy.blackjack.infrastructure.web.dto.PlayerResponse;
import org.springframework.stereotype.Component;

@Component
public class PlayerWebMapper {

    public PlayerResponse toResponse(Player player) {
        return new PlayerResponse(
                player.getId(),
                player.getUsername(),
                player.getWins(),
                player.getLosses(),
                player.getWinRate(),
                player.getCreatedAt(),
                player.getUpdatedAt()
        );
    }
}
