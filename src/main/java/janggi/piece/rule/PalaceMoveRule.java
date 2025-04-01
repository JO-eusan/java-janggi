package janggi.piece.rule;

import janggi.game.team.Team;
import janggi.point.PalacePoints;
import janggi.point.Point;
import janggi.point.PointDistance;
import janggi.point.Route;
import java.util.List;

public class PalaceMoveRule implements MoveRule {

    private static final double CARDINAL_MOVE_DISTANCE = 1;
    private static final double DIAGONAL_MOVE_DISTANCE = Math.sqrt(2);

    private final Team team;

    public PalaceMoveRule(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(Point startPoint, Point targetPoint) {
        PointDistance distance = PointDistance.calculate(startPoint, targetPoint);

        if (PalacePoints.isOutOfPalaceRange(team, targetPoint)) {
            return false;
        }
        if (PalacePoints.isInPalaceWithMovableCardinal(team, startPoint)) {
            return distance.isSameWith(CARDINAL_MOVE_DISTANCE);
        }
        if (PalacePoints.isInPalaceWithMovableDiagonal(team, startPoint)) {
            return distance.isLessAndEqualTo(DIAGONAL_MOVE_DISTANCE);
        }
        return false;
    }

    @Override
    public Route searchRoute(Point startPoint, Point targetPoint) {
        return new Route(List.of(), targetPoint);
    }
}
