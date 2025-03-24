package janggi.point;

import janggi.game.Team;
import java.util.List;

public enum PalacePoints {
    HAN(List.of(
        new Point(0, 3),
        new Point(0, 4),
        new Point(0, 5),
        new Point(1, 3),
        new Point(1, 4),
        new Point(1, 5),
        new Point(2, 3),
        new Point(2, 4),
        new Point(2, 5)
    )),
    CHO(List.of(
        new Point(7, 3),
        new Point(7, 4),
        new Point(7, 5),
        new Point(8, 3),
        new Point(8, 4),
        new Point(8, 5),
        new Point(9, 3),
        new Point(9, 4),
        new Point(9, 5)
    ));

    private final List<Point> pointsInPalace;

    PalacePoints(List<Point> pointsInPalace) {
        this.pointsInPalace = pointsInPalace;
    }

    public static boolean isInPalaceRange(Team team, Point point) {
        if (team == Team.HAN) {
            return HAN.pointsInPalace.contains(point);
        }
        if (team == Team.CHO) {
            return CHO.pointsInPalace.contains(point);
        }
        throw new IllegalArgumentException("제공되지 않는 팀입니다.");
    }
}
