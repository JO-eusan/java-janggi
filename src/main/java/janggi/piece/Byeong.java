package janggi.piece;

import janggi.game.team.Team;
import janggi.piece.type.PieceType;
import janggi.point.PalacePoints;
import janggi.point.Point;
import janggi.point.PointDistance;
import janggi.point.Route;
import java.util.List;

public class Byeong implements Movable {

    private static final double MOVE_DISTANCE_OUT_PALACE = 1;
    private static final double MOVE_DISTANCE_IN_PALACE = Math.sqrt(2);

    private final PieceType type;
    private final Team team;

    public Byeong(Team team) {
        this.type = PieceType.BYEONG;
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
    public Team getTeam() {
        return this.team;
    }

    @Override
    public String getName() {
        return type.getText();
    }

    @Override
    public double getScore(Team team) {
        return type.getScore() + team.getExtraScore();
    }
}
