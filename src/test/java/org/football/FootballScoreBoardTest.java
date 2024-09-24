package org.football;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class FootballScoreBoardTest {

    FootballScoreBoard scoreBoard;

    @BeforeEach
    public void prep() {
        scoreBoard = new FootballScoreBoard();
    }

    @ParameterizedTest
    @MethodSource("invalidTeamNames")
    public void startNewGame_withInvalidNamesForTeams_shouldThrowException(String homeTeam, String awayTeam) {
        assertThrows(IllegalArgumentException.class, () -> scoreBoard.startNewGame(homeTeam, awayTeam));
    }

    @ParameterizedTest
    @MethodSource("validTeamNames")
    public void startNewGame_withValidNamesForTeams_shouldReturnGameId(String homeTeam, String awayTeam) {
        assertNotNull(scoreBoard.startNewGame(homeTeam, awayTeam));
    }

    @Test
    public void startNewGame_withTeamsThatAlreadyHaveAGameActive_shouldThrowException() {
        scoreBoard.startNewGame(HOME_TEAM, AWAY_TEAM);
        assertThrows(IllegalStateException.class, () -> scoreBoard.startNewGame(HOME_TEAM, AWAY_TEAM));
    }

    @Test
    public void finishGame_withUnfinishedGame_shouldFinishIt() {
        Integer gameId = scoreBoard.startNewGame(HOME_TEAM, AWAY_TEAM);
        assertTrue(scoreBoard.finishGame(gameId));
    }

    @Test
    public void finishGame_withFinishedGame_shouldReturnFalse() {
        Integer gameId = scoreBoard.startNewGame(HOME_TEAM, AWAY_TEAM);
        scoreBoard.finishGame(gameId);
        assertFalse(scoreBoard.finishGame(gameId));
    }

    @Test
    public void finishGame_withNonExistentGameId_shouldReturnFalse() {
        assertFalse(scoreBoard.finishGame(-1));
    }

    @ParameterizedTest
    @MethodSource("invalidScores")
    public void updateScore_withInvalidScores_shouldReturnFalse(Integer homeScore, Integer awayScore) {
        Integer gameId = scoreBoard.startNewGame(HOME_TEAM, AWAY_TEAM);
        assertFalse(scoreBoard.updateScores(gameId, homeScore, awayScore));
    }

    @ParameterizedTest
    @MethodSource("validScores")
    public void updateScore_withValidScores_shouldReturnTrue(Integer homeScore, Integer awayScore) {
        Integer gameId = scoreBoard.startNewGame(HOME_TEAM, AWAY_TEAM);
        assertTrue(scoreBoard.updateScores(gameId, homeScore, awayScore));
    }

    @Test
    public void getSummary_withNoActiveGames_shouldReturnEmptyList() {
        assertEquals("", scoreBoard.getSummary());
    }

    @Test
    public void getSummary_withFewScorelessGames_shouldReturnListOfScorelessGames() {
        scoreBoard.startNewGame("Mexico", "Canada");
        scoreBoard.startNewGame("Spain", "Brazil");
        scoreBoard.startNewGame("Germany", "France");

        assertEquals("""
                1. Germany 0 - France 0
                2. Spain 0 - Brazil 0
                3. Mexico 0 - Canada 0
                """, scoreBoard.getSummary());
    }

    @Test
    public void getSummary_withSetOfActiveGames_shouldReturnAppropriatlyOrderedList() {
        scoreBoard.updateScores(scoreBoard.startNewGame("Mexico", "Canada"), 0, 5);
        scoreBoard.updateScores(scoreBoard.startNewGame("Spain", "Brazil"), 10, 2);
        scoreBoard.updateScores(scoreBoard.startNewGame("Germany", "France"), 2, 2);
        scoreBoard.updateScores(scoreBoard.startNewGame("Uruguay", "Italy"), 6, 6);
        scoreBoard.updateScores(scoreBoard.startNewGame("Argentina", "Australia"), 3, 1);

        assertEquals("""
                1. Uruguay 6 - Italy 6
                2. Spain 10 - Brazil 2
                3. Mexico 0 - Canada 5
                4. Argentina 3 - Australia 1
                5. Germany 2 - France 2
                """, scoreBoard.getSummary());
    }

    @Test
    public void getSummary_withScoreboardAfterSomeGamesHaveFinished_shouldReturnListOfOnlyActiveGames() {
        scoreBoard.updateScores(scoreBoard.startNewGame("Mexico", "Canada"), 0, 5);
        scoreBoard.updateScores(scoreBoard.startNewGame("Spain", "Brazil"), 10, 2);
        scoreBoard.updateScores(scoreBoard.startNewGame("Germany", "France"), 2, 2);
        Integer uruguayItalyGameId = scoreBoard.startNewGame("Uruguay", "Italy");
        scoreBoard.updateScores(uruguayItalyGameId, 6, 6);
        scoreBoard.finishGame(uruguayItalyGameId);
        Integer argentinaAustraliaGameId = scoreBoard.startNewGame("Argentina", "Australia");
        scoreBoard.updateScores(argentinaAustraliaGameId, 3, 1);
        scoreBoard.finishGame(argentinaAustraliaGameId);

        assertEquals("""
                1. Spain 10 - Brazil 2
                2. Mexico 0 - Canada 5
                3. Germany 2 - France 2
                """, scoreBoard.getSummary());
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