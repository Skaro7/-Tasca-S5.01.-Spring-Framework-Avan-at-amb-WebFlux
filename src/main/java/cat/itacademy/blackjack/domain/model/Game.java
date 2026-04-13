package cat.itacademy.blackjack.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Aggregate Root — represents a Blackjack game stored in MongoDB.
 * Contains all game logic: dealing, hit, stand, dealer play.
 */
public class Game {

    private String id;            // MongoDB ObjectId
    private Long playerId;        // FK to MySQL player
    private String playerName;
    private Hand playerHand;
    private Hand dealerHand;
    private Deck deck;
    private GameStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ── Factory method: start a new game ──────────────────────────────
    public static Game create(Long playerId, String playerName) {
        Game game = new Game();
        game.playerId    = Objects.requireNonNull(playerId);
        game.playerName  = Objects.requireNonNull(playerName);
        game.status      = GameStatus.IN_PROGRESS;
        game.createdAt   = LocalDateTime.now();
        game.updatedAt   = LocalDateTime.now();

        // Deal initial two cards to player and dealer
        Deck deck = new Deck();
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        Deck.DrawResult d1 = deck.draw();
        playerHand = playerHand.addCard(d1.card()); deck = d1.remainingDeck();

        Deck.DrawResult d2 = deck.draw();
        dealerHand = dealerHand.addCard(d2.card()); deck = d2.remainingDeck();

        Deck.DrawResult d3 = deck.draw();
        playerHand = playerHand.addCard(d3.card()); deck = d3.remainingDeck();

        Deck.DrawResult d4 = deck.draw();
        dealerHand = dealerHand.addCard(d4.card()); deck = d4.remainingDeck();

        game.playerHand = playerHand;
        game.dealerHand = dealerHand;
        game.deck       = deck;

        if (playerHand.isBlackjack()) {
            game.status = dealerHand.isBlackjack() ? GameStatus.PUSH : GameStatus.PLAYER_WINS;
        }

        return game;
    }

    public void hit() {
        assertInProgress();
        Deck.DrawResult result = deck.draw();
        playerHand = playerHand.addCard(result.card());
        deck       = result.remainingDeck();
        updatedAt  = LocalDateTime.now();

        if (playerHand.isBust()) {
            status = GameStatus.DEALER_WINS;
        }
    }

    public void stand() {
        assertInProgress();

        while (dealerHand.getScore() < 17) {
            Deck.DrawResult result = deck.draw();
            dealerHand = dealerHand.addCard(result.card());
            deck       = result.remainingDeck();
        }
        updatedAt = LocalDateTime.now();
        resolveGame();
    }

    private void assertInProgress() {
        if (status != GameStatus.IN_PROGRESS) {
            throw new IllegalStateException("Game " + id + " is already finished");
        }
    }

    private void resolveGame() {
        int playerScore = playerHand.getScore();
        int dealerScore = dealerHand.getScore();

        if (dealerHand.isBust() || playerScore > dealerScore) {
            status = GameStatus.PLAYER_WINS;
        } else if (playerScore < dealerScore) {
            status = GameStatus.DEALER_WINS;
        } else {
            status = GameStatus.PUSH;
        }
    }

        public String getId()                  { return id; }
    public void setId(String id)           { this.id = id; }
    public Long getPlayerId()              { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }
    public String getPlayerName()          { return playerName; }
    public void setPlayerName(String n)    { this.playerName = n; }
    public Hand getPlayerHand()            { return playerHand; }
    public void setPlayerHand(Hand h)      { this.playerHand = h; }
    public Hand getDealerHand()            { return dealerHand; }
    public void setDealerHand(Hand h)      { this.dealerHand = h; }
    public Deck getDeck()                  { return deck; }
    public void setDeck(Deck deck)         { this.deck = deck; }
    public GameStatus getStatus()          { return status; }
    public void setStatus(GameStatus s)    { this.status = s; }
    public LocalDateTime getCreatedAt()    { return createdAt; }
    public void setCreatedAt(LocalDateTime t) { this.createdAt = t; }
    public LocalDateTime getUpdatedAt()    { return updatedAt; }
    public void setUpdatedAt(LocalDateTime t) { this.updatedAt = t; }
}