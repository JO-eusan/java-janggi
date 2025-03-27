package janggi.game;

import janggi.piece.InitialPieces;
import janggi.piece.Movable;
import janggi.piece.RunningPieces;
import janggi.point.Point;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Board {

    private final RunningPieces runningPieces;
    private Team turn;

    public Board(RunningPieces runningPieces, Team turn) {
        this.runningPieces = runningPieces;
        this.turn = turn;
    }

    public static Board putPiecesOnPoint(Team startTeam) {
        RunningPieces runningPieces = InitialPieces.getAllPieces();
        return new Board(runningPieces, startTeam);
    }

    public void reverseTurn() {
        this.turn = turn.reverse();
    }

    public int countPieces(String name) {
        return runningPieces.findPiecesByName(name).size();
    }

    public void move(Point startPoint, Point targetPoint) {
        validateTurn(runningPieces.findPieceByPoint(startPoint));

        runningPieces.validateMovable(startPoint, targetPoint);
        runningPieces.updatePiece(startPoint, targetPoint);
    }

    private void validateTurn(Movable movingPiece) {
        if (turn != movingPiece.getTeam()) {
            throw new IllegalArgumentException(turn.getText() + "의 기물만 이동할 수 있습니다.");
        }
    }

    public TeamScore calculateScoreOfAllTeam() {
        Map<Team, Double> teamScore = Arrays.stream(Team.values())
            .collect(Collectors.toMap(team -> team, runningPieces::calculateTotalScore));
        return new TeamScore(teamScore);
    }

    public Team decideWinner(String decisionPiece) {
        List<Movable> pieces = runningPieces.findPiecesByName(decisionPiece);
        return calculateScoreOfAllTeam().judgeWinner(pieces);
    }

    public RunningPieces getRunningPieces() {
        return runningPieces;
    }

    public Team getTurn() {
        return turn;
    }
}
