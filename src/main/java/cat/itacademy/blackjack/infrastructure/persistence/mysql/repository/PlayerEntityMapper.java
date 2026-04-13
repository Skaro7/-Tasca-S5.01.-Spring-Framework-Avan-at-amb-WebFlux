package cat.itacademy.blackjack.infrastructure.persistence.mysql.repository;

import cat.itacademy.blackjack.domain.model.Player;
import cat.itacademy.blackjack.infrastructure.persistence.mysql.entity.PlayerEntity;
import org.springframework.stereotype.Component;

@Component
public class PlayerEntityMapper {

    public PlayerEntity toEntity(Player player) {
        PlayerEntity entity = new PlayerEntity();
        entity.setId(player.getId());
        entity.setUsername(player.getUsername());
        entity.setWins(player.getWins());
        entity.setLosses(player.getLosses());
        entity.setWinRate(player.getWinRate());
        entity.setCreatedAt(player.getCreatedAt());
        entity.setUpdatedAt(player.getUpdatedAt());
        return entity;
    }

    public Player toDomain(PlayerEntity entity) {
        Player player = new Player();
        player.setId(entity.getId());
        player.setUsername(entity.getUsername());
        player.setWins(entity.getWins());
        player.setLosses(entity.getLosses());
        player.setWinRate(entity.getWinRate());
        player.setCreatedAt(entity.getCreatedAt());
        player.setUpdatedAt(entity.getUpdatedAt());
        return player;
    }
}
