package org.football;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class FootballGameTest {

    @ParameterizedTest
    @MethodSource("invalidTeamNames")
    public void construct_withInvalidNames_shouldThrowException(String homeTeam, String awayTeam) {
        assertThrows(IllegalArgumentException.class, () -> new FootballGame(homeTeam, awayTeam));
    }

    @ParameterizedTest
    @MethodSource("validTeamNames")
    public void construct_withValidNames_shouldCreateNewObject(String homeTeam, String awayTeam) {
        FootballGame footballGame = new FootballGame(homeTeam, awayTeam);
        assertEquals(0, footballGame.getHomeScore());
        assertEquals(0, footballGame.getAwayScore());
    }

    @ParameterizedTest
    @MethodSource("invalidScores")
    public void updateScore_withInvalidScores_shouldReturnFalse(Integer homeScore, Integer awayScore) {
        FootballGame game = new FootballGame(HOME_TEAM, AWAY_TEAM);
        assertFalse(game.updateScores(homeScore, awayScore));
    }

    @ParameterizedTest
    @MethodSource("validScores")
    public void updateScore_withValidScores_shouldReturnTrue(Integer homeScore, Integer awayScore) {
        FootballGame game = new FootballGame(HOME_TEAM, AWAY_TEAM);
        assertTrue(game.updateScores(homeScore, awayScore));
    }

    private static Stream<Arguments> invalidTeamNames() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of("", ""),
                Arguments.of("  ", null),
                Arguments.of(null, "  "),
                Arguments.of(null, ""),
                Arguments.of("", null)
        );
    }

    private static Stream<Arguments> validTeamNames() {
        return Stream.of(
                Arguments.of("Mexico", "Canada"),
                Arguments.of("Spain", "Brazil"),
                Arguments.of("Germany", "France"),
                Arguments.of("Uruguay", "Italy"),
                Arguments.of("Argentina", "Australia")
        );
    }

    private static Stream<Arguments> invalidScores() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, 0),
                Arguments.of(0, null),
                Arguments.of(0, -1),
                Arguments.of(-1, 0),
                Arguments.of(-1, -1),
                Arguments.of(-100, 0),
                Arguments.of(0, -100)
        );
    }

    private static Stream<Arguments> validScores() {
        return Stream.of(
                Arguments.of(0, 5),
                Arguments.of(10, 2),
                Arguments.of(2, 2),
                Arguments.of(6, 6),
                Arguments.of(3, 1)
        );
    }

    private final static String HOME_TEAM = "Mexico";
    private final static String AWAY_TEAM = "Canada";
}
