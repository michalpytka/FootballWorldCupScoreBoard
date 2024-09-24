package org.football;

import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class FootballScoreBoard {

    private final LinkedHashMap<Integer, FootballGame> gameMap;
    private Integer gameIdCounter;

    public FootballScoreBoard() {
        gameMap = new LinkedHashMap<>();
        gameIdCounter = 0;
    }

    public Integer startNewGame(String homeTeam, String awayTeam) {
        checkIfTeamHasActiveGame(homeTeam);
        checkIfTeamHasActiveGame(awayTeam);

        gameMap.put(gameIdCounter, new FootballGame(homeTeam, awayTeam));
        return gameIdCounter++;
    }

    public boolean finishGame(Integer gameId) {
        return gameMap.remove(gameId) != null;
    }

    public boolean updateScores(Integer gameId, Integer homeScore, Integer awayScore) {
        FootballGame game = gameMap.get(gameId);
        if (game == null) {
            return false;
        }
        return game.updateScores(homeScore, awayScore);
    }

    public String getSummary() {
        final StringBuilder builder = new StringBuilder();
        AtomicReference<Integer> ordinalListNumber = new AtomicReference<>(1);
        gameMap.sequencedValues()
                .reversed()
                .stream()
                .sorted((game1, game2) -> {
                    final Integer game1Sum = game1.getHomeScore() + game1.getAwayScore();
                    final Integer game2Sum = game2.getHomeScore() + game2.getAwayScore();
                    return game2Sum.compareTo(game1Sum);
                })
                .forEach(footballGame -> {
                    builder.append(ordinalListNumber)
                            .append(". ")
                            .append(footballGame.getHomeTeam())
                            .append(" ")
                            .append(footballGame.getHomeScore())
                            .append(" - ")
                            .append(footballGame.getAwayTeam())
                            .append(" ")
                            .append(footballGame.getAwayScore())
                            .append("\n");
                    ordinalListNumber.getAndSet(ordinalListNumber.get() + 1);
                });
        return builder.toString();
    }

    private void checkIfTeamHasActiveGame(String name) {
        if (gameMap.values().stream().anyMatch(footballGame -> footballGame.getHomeTeam().equals(name)) ||
                gameMap.values().stream().anyMatch(footballGame -> footballGame.getAwayTeam().equals(name))) {
            throw new IllegalStateException("Team " + name + " already has a game active");
        }
    }
}
