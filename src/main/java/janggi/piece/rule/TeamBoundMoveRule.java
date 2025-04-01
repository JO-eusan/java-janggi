package janggi.piece.rule;

import janggi.game.team.Team;
import janggi.point.PalacePoints;
import janggi.point.Point;
import janggi.point.PointDistance;
import janggi.point.Route;
import java.util.List;

public class TeamBoundMoveRule implements MoveRule {

    private final double moveDistanceOutPalace;
    private final double moveDistanceInPalace;
    private final Team team;

    public TeamBoundMoveRule(double moveDistanceOutPalace, double moveDistanceInPalace, Team team) {
        this.moveDistanceOutPalace = moveDistanceOutPalace;
        this.moveDistanceInPalace = moveDistanceInPalace;
        this.team = team;
    }

    @Override
    public boolean canMove(Point startPoint, Point targetPoint) {
        PointDistance distance = PointDistance.calculate(startPoint, targetPoint);

        if ((team == Team.CHO && startPoint.isRowLessThan(targetPoint))
            || (team == Team.HAN && startPoint.isRowBiggerThan(targetPoint))) {
            return false;
        }
        if (PalacePoints.isInPalaceWithMovableDiagonal(startPoint)
            && PalacePoints.isInPalaceWithMovableDiagonal(targetPoint)) {
            return distance.isLessAndEqualTo(moveDistanceInPalace);
        }
        return distance.isSameWith(moveDistanceOutPalace);
    }

    @Override
    public Route searchRoute(Point startPoint, Point targetPoint) {
        return new Route(List.of(), targetPoint);
    }
}
