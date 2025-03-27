package janggi.point;

import janggi.game.Team;
import janggi.piece.Movable;
import janggi.piece.RunningPieces;
import java.util.List;
import java.util.Set;

public class Route {

    private static final String NEEDED_HURDLE_PIECE = "포";
    private static final int NEEDED_HURDLE_NUMBER = 1;

    private final List<Point> path;
    private final Point targetPoint;

    public Route(List<Point> path, Point targetPoint) {
        this.path = path;
        this.targetPoint = targetPoint;
    }

    public void validateRoute(Point startPoint, RunningPieces pieces) {
        Movable movingPiece = pieces.findPieceByPoint(startPoint);

        if (NEEDED_HURDLE_PIECE.equals(movingPiece.getName())) {
            checkRouteWithHurdle(startPoint, pieces);
            return;
        }
        checkRouteWithoutHurdles(startPoint, pieces);
    }

    private void checkRouteWithHurdle(Point startPoint, RunningPieces pieces) {
        if (hasTargetPointHurdles(startPoint, pieces)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
        if (countJumperForPo(pieces) != NEEDED_HURDLE_NUMBER) {
            throw new IllegalArgumentException("포는 포를 제외한 하나의 기물만 필요합니다.");
        }
        if (pieces.existPieceOnPoint(targetPoint)
            && NEEDED_HURDLE_PIECE.equals(pieces.findPieceByPoint(targetPoint).getName())) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
    }

    private void checkRouteWithoutHurdles(Point startPoint, RunningPieces pieces) {
        if (!findRouteHurdles(pieces.getPoints()).isEmpty()
            || hasTargetPointHurdles(startPoint, pieces)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
    }

    private boolean hasTargetPointHurdles(Point startPoint, RunningPieces pieces) {
        if (pieces.existPieceOnPoint(targetPoint)) {
            Team startPieceTeam = pieces.findPieceByPoint(startPoint).getTeam();
            Team targetPieceTeam = pieces.findPieceByPoint(targetPoint).getTeam();

            return startPieceTeam == targetPieceTeam;
        }
        return false;
    }

    private int countJumperForPo(RunningPieces pieces) {
        return (int) findRouteHurdles(pieces.getPoints()).stream()
            .filter(point -> !NEEDED_HURDLE_PIECE.equals(pieces.findPieceByPoint(point).getName()))
            .count();
    }

    private List<Point> findRouteHurdles(Set<Point> pointsWithPiece) {
        return path.stream()
            .filter(pointsWithPiece::contains)
            .toList();
    }

    public List<Point> getPath() {
        return path;
    }

    public Point getTargetPoint() {
        return targetPoint;
    }
}
