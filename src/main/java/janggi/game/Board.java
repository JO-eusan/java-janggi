package janggi.game;

import janggi.piece.Movable;
import janggi.point.Point;
import janggi.point.Route;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Board {

    private final Map<Point, Movable> runningPieces;
    private Team turn;

    public Board(Map<Point, Movable> runningPieces, Team startTeam) {
        this.runningPieces = runningPieces;
        this.turn = startTeam;
    }

    public static Board putPiecesOnPoint(Team startTeam) {
        Map<Point, Movable> runningPieces = InitialPieces.getAllPieces();
        return new Board(runningPieces, startTeam);
    }

    public void reverseTurn() {
        this.turn = turn.reverse();
    }

    public List<Movable> findPiecesByName(String pieceName) {
        return runningPieces.values().stream()
            .filter(piece -> pieceName.equals(piece.getName()))
            .toList();
    }

    public Movable findPieceByPoint(Point point) {
        if (runningPieces.containsKey(point)) {
            return runningPieces.get(point);
        }
        throw new IllegalArgumentException("해당 좌표에 기물이 존재하지 않습니다.");
    }

    public void move(Point startPoint, Point targetPoint) {
        Movable movingPiece = findPieceByPoint(startPoint);
        validateMovable(startPoint, targetPoint);

        Route route = movingPiece.findRoute(startPoint, targetPoint);
        route.validateRoute(startPoint, this);

        runningPieces.remove(startPoint);
        runningPieces.remove(targetPoint);
        runningPieces.put(targetPoint, movingPiece);
    }

    private void validateMovable(Point startPoint, Point targetPoint) {
        Movable movingPiece = findPieceByPoint(startPoint);

        if (turn != movingPiece.getTeam()) {
            throw new IllegalArgumentException(turn.getText() + "의 기물만 이동할 수 있습니다.");
        }
        if (!movingPiece.isInMovingRange(startPoint, targetPoint)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
    }

    public TeamScore calculateScoreOfAllTeam() {
        Map<Team, Double> teamScore = Arrays.stream(Team.values())
            .collect(Collectors.toMap(team -> team, this::calculateTotalScore));
        return new TeamScore(teamScore);
    }

    private double calculateTotalScore(Team team) {
        return runningPieces.values().stream()
            .filter(piece -> team == piece.getTeam())
            .mapToDouble(piece -> piece.getScore(team))
            .sum();
    }

    public Map<Point, Movable> getRunningPieces() {
        return Collections.unmodifiableMap(runningPieces);
    }

    public Team getTurn() {
        return turn;
    }
}
