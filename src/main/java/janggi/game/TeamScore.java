package janggi.game;

import janggi.piece.Movable;
import java.util.List;
import java.util.Map;

public class TeamScore {

    private static final int WINNER_DECISION_PIECE_SIZE = 1;

    private final Map<Team, Double> teamScore;

    public TeamScore(Map<Team, Double> teamScore) {
        this.teamScore = teamScore;
    }

    public double findScoreByTeam(Team team) {
        return teamScore.get(team);
    }

    public Team judgeWinner(List<Movable> decisionPiece) {
        if (decisionPiece.size() == WINNER_DECISION_PIECE_SIZE) {
            return decisionPiece.getFirst().getTeam();
        }
        if (teamScore.get(Team.CHO) > teamScore.get(Team.HAN)) {
            return Team.CHO;
        }
        return Team.HAN;
    }
}
