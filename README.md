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
- Team names are to be treated as invalid if they are null or blank
- A team that already has a game active cannot take part in a new game
- As the update score operation take in two scores as parameters, it is assumed that any combination of non-null and non-negative numbers is valid
- Getting summary from a board that has no active games returns an empty string
