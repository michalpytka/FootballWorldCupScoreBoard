package org.football;

public class FootballGame {
    private final String homeTeam;
    private final String awayTeam;

    public FootballGame(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public boolean updateScores(Integer homeScore, Integer awayScore) {
        return false;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }
}
