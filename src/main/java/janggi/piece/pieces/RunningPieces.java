package janggi.piece.pieces;

import janggi.game.team.Team;
import janggi.piece.Movable;
import janggi.point.Point;
import janggi.point.Route;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RunningPieces {

    private final Map<Point, Movable> runningPieces;

    public RunningPieces(Map<Point, Movable> runningPieces) {
        this.runningPieces = runningPieces;
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

    public boolean existPieceOnPoint(Point point) {
        return runningPieces.containsKey(point);
    }

    public void validateMovable(Point startPoint, Point targetPoint) {
        Movable movingPiece = findPieceByPoint(startPoint);
        Route route = movingPiece.findRoute(startPoint, targetPoint);

        if (!movingPiece.isInMovingRange(startPoint, targetPoint)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
        route.validateRoute(startPoint, this);
    }

    public void updatePiece(Point startPoint, Point targetPoint) {
        Movable movingPiece = findPieceByPoint(startPoint);

        runningPieces.remove(startPoint);
        runningPieces.remove(targetPoint);
        runningPieces.put(targetPoint, movingPiece);
    }

    public double calculateTotalScore(Team team) {
        return runningPieces.values().stream()
            .filter(piece -> team == piece.getTeam())
            .mapToDouble(piece -> piece.getScore(team))
            .sum();
    }

    public Set<Point> getPoints() {
        return Collections.unmodifiableSet(runningPieces.keySet());
    }

    public Map<Point, Movable> getRunningPieces() {
        return Collections.unmodifiableMap(runningPieces);
    }
}
