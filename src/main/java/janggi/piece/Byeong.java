package janggi.piece;

import janggi.game.Team;
import janggi.point.PalacePoints;
import janggi.point.Point;
import janggi.point.PointDistance;
import janggi.point.Route;
import java.util.List;

public class Byeong implements Movable {

    private static final String NAME = "병";
    private static final double SCORE = 2.0;
    private static final double MOVE_DISTANCE_OUT_PALACE = 1;
    private static final double MOVE_DISTANCE_IN_PALACE = Math.sqrt(2);

    private final Team team;

    public Byeong(Team team) {
        this.team = team;
    }

    @Override
    public boolean isInMovingRange(Point startPoint, Point targetPoint) {
        PointDistance distance = PointDistance.calculate(startPoint, targetPoint);

        if ((team == Team.CHO && startPoint.isRowLessThan(targetPoint))
            || (team == Team.HAN && startPoint.isRowBiggerThan(targetPoint))) {
            return false;
        }
        if (PalacePoints.isInPalaceWithMovableDiagonal(startPoint)
            && PalacePoints.isInPalaceWithMovableDiagonal(targetPoint)) {
            return distance.isLessAndEqualTo(MOVE_DISTANCE_IN_PALACE);
        }
        return distance.isSameWith(MOVE_DISTANCE_OUT_PALACE);
    }

    @Override
    public Route findRoute(Point startPoint, Point targetPoint) {
        return new Route(List.of(), targetPoint);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public double getScore(Team team) {
        return SCORE + team.getExtraScore();
    }

    @Override
    public Team getTeam() {
        return this.team;
    }
}
