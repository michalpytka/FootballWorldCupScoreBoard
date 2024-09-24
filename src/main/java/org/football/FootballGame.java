package org.football;

import org.apache.commons.lang3.StringUtils;

public class FootballGame {
    private final String homeTeam;
    private final String awayTeam;
    private Integer homeScore;
    private Integer awayScore;

    public FootballGame(String homeTeam, String awayTeam) {
        validateName(homeTeam);
        validateName(awayTeam);

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
    }

    public boolean updateScores(Integer homeScore, Integer awayScore) {
        if (validScore(homeScore) && validScore(awayScore)) {
            this.homeScore = homeScore;
            this.awayScore = awayScore;
            return true;
        }
        return false;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public Integer getAwayScore() {
        return awayScore;
    }

    private void validateName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("The following name does not pass validation:" + name);
        }
    }

    private boolean validScore(Integer score) {
        return score != null && score >= 0;
    }
}
