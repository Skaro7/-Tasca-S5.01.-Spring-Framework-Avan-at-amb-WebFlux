package cat.itacademy.blackjack.domain.model;

import java.time.LocalDateTime;

public class Player {

    private Long id;
    private String username;
    private int wins;
    private int losses;
    private double winRate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Player create(String username) {
        Player player = new Player();
        player.username  = username;
        player.wins      = 0;
        player.losses    = 0;
        player.winRate   = 0.0;
        player.createdAt = LocalDateTime.now();
        player.updatedAt = LocalDateTime.now();
        return player;
    }

    public void recordWin() {
        this.wins++;
        recalculateWinRate();
        this.updatedAt = LocalDateTime.now();
    }

    public void recordLoss() {
        this.losses++;
        recalculateWinRate();
        this.updatedAt = LocalDateTime.now();
    }

    public void rename(String newUsername) {
        if (newUsername == null || newUsername.isBlank()) {
            throw new IllegalArgumentException("Username must not be blank");
        }
        this.username  = newUsername;
        this.updatedAt = LocalDateTime.now();
    }

    private void recalculateWinRate() {
        int total = wins + losses;
        this.winRate = total == 0 ? 0.0 : (double) wins / total * 100;
    }

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