package cat.itacademy.blackjack.infrastructure.persistence.mysql.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * R2DBC entity mapped to the MySQL 'players' table.
 * No JPA annotations — uses Spring Data R2DBC.
 */
@Table("players")
public class PlayerEntity {

    @Id
    private Long id;

    @Column("username")
    private String username;

    @Column("wins")
    private int wins;

    @Column("losses")
    private int losses;

    @Column("win_rate")
    private double winRate;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    // Getters & Setters
    public Long getId()                       { return id; }
    public void setId(Long id)                { this.id = id; }
    public String getUsername()               { return username; }
    public void setUsername(String username)  { this.username = username; }
    public int getWins()                      { return wins; }
    public void setWins(int wins)             { this.wins = wins; }
    public int getLosses()                    { return losses; }
    public void setLosses(int losses)         { this.losses = losses; }
    public double getWinRate()                { return winRate; }
    public void setWinRate(double winRate)    { this.winRate = winRate; }
    public LocalDateTime getCreatedAt()       { return createdAt; }
    public void setCreatedAt(LocalDateTime t) { this.createdAt = t; }
    public LocalDateTime getUpdatedAt()       { return updatedAt; }
    public void setUpdatedAt(LocalDateTime t) { this.updatedAt = t; }
}