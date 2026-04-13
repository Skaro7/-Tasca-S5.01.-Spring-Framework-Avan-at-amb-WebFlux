package cat.itacademy.blackjack.infrastructure.web.dto;

import java.util.List;

public record HandResponse(List<CardResponse> cards, int score) {}
