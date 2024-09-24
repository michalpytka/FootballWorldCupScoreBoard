package org.football;

import java.util.HashMap;

public class FootballScoreBoard {

    final HashMap<Integer, FootballGame> gameMap;
    Integer gameIdCounter;

    public FootballScoreBoard() {
        gameMap = new HashMap<>();
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

        game.updateScores(homeScore, awayScore);
        return true;
    }

    public String getSummary() {
        return null;
    }

    private void checkIfTeamHasActiveGame(String name) {
        if (gameMap.values().stream().anyMatch(footballGame -> footballGame.getHomeTeam().equals(name)) ||
                gameMap.values().stream().anyMatch(footballGame -> footballGame.getAwayTeam().equals(name))) {
            throw new IllegalStateException("Team " + name + " already has a game active");
        }
    }
}
