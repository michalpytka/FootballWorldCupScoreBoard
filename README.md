# Football World Cup Score Board - Coding Exercise

## Guidelines
- Pure Java implementation (LTS 21)
- Simple module (No API, nor persistence)
- TDD

## Functional requirements
- Starting a game initializes it with a 0-0 score
- In the summary, games are to be ordered by their total score
- In case of multiple games having the same total score, order of insertions should determine order in summary, starting with the newest entry

## Assumptions
- Team names are to be treated as invalid, if they are null or blank
- A team that already has a game active cannot take part in a new game
- As the update score operation take in two scores as parameters, it is assumed that any combination of non-null and non-negative numbers is valid
- Getting summary from a board that has no active games returns an empty string
- Both teams in a game cannot have the same name

## Further comments
- Apache commons lang was added to project for the singular use of StringUtils.isBlank() method. In case of this exercise it might be overkill, but in a regular setting this dependency might be already present in project
- Instead of assigning artificial ids to started matches, they could have been identifiable by a combination of home and away team's names. Ids where used as that's most often present when working with persistable objects
- Depending on actual use case, event sourcing might be a good design pattern for score tracking
- As the exercise specified otherwise, the responsibility of ScoreBoard could not be distributed. It might be beneficial to move starting, finishing and score updating functions to Match itself. Thus, Scoreboard would be only responsible for showing results (as the name suggests).
