package janggi.point;

import janggi.game.Team;
import java.util.List;

public enum PalacePoints {
    HAN(List.of(
        new Point(0, 4),
        new Point(1, 3),
        new Point(1, 5),
        new Point(2, 4)),
        List.of(
            new Point(0, 3),
            new Point(0, 5),
            new Point(1, 4),
            new Point(2, 3),
            new Point(2, 5))
    ),
    CHO(List.of(
        new Point(7, 4),
        new Point(8, 3),
        new Point(8, 5),
        new Point(9, 4)),
        List.of(
            new Point(7, 3),
            new Point(7, 5),
            new Point(8, 4),
            new Point(9, 3),
            new Point(9, 5))
    );

    private final List<Point> cardinalPoints;
    private final List<Point> diagonalPoints;

    PalacePoints(List<Point> cardinalPoints, List<Point> diagonalPoints) {
        this.cardinalPoints = cardinalPoints;
        this.diagonalPoints = diagonalPoints;
    }

    public static boolean isInPalaceWithMovableCardinal(Team team, Point point) {
        if (team == Team.HAN) {
            return HAN.cardinalPoints.contains(point);
        }
        if (team == Team.CHO) {
            return CHO.cardinalPoints.contains(point);
        }
        throw new IllegalArgumentException("제공되지 않는 팀입니다.");
    }

    public static boolean isInPalaceWithMovableCardinal(Point point) {
        return HAN.cardinalPoints.contains(point) || CHO.cardinalPoints.contains(point);
    }

    public static boolean isInPalaceWithMovableDiagonal(Team team, Point point) {
        if (team == Team.HAN) {
            return HAN.diagonalPoints.contains(point);
        }
        if (team == Team.CHO) {
            return CHO.diagonalPoints.contains(point);
        }
        throw new IllegalArgumentException("제공되지 않는 팀입니다.");
    }

    public static boolean isInPalaceWithMovableDiagonal(Point point) {
        return HAN.diagonalPoints.contains(point) || CHO.diagonalPoints.contains(point);
    }

    public static boolean isInPalaceRange(Team team, Point point) {
        if (team == Team.HAN) {
            return HAN.cardinalPoints.contains(point) || HAN.diagonalPoints.contains(point);
        }
        if (team == Team.CHO) {
            return CHO.cardinalPoints.contains(point) || CHO.diagonalPoints.contains(point);
        }
        throw new IllegalArgumentException("제공되지 않는 팀입니다.");
    }

    public static boolean isOutOfPalaceRange(Team team, Point point) {
        return !isInPalaceRange(team, point);
    }
}
