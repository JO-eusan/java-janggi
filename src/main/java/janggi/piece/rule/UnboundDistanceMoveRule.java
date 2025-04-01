package janggi.piece.rule;

import janggi.point.Direction;
import janggi.point.PalacePoints;
import janggi.point.Point;
import janggi.point.PointDistance;
import janggi.point.Route;
import java.util.ArrayList;
import java.util.List;

public class UnboundDistanceMoveRule implements MoveRule {

    @Override
    public boolean canMove(Point startPoint, Point targetPoint) {
        if (PalacePoints.isInPalaceWithMovableDiagonal(startPoint)
            && PalacePoints.isInPalaceWithMovableDiagonal(targetPoint)) {
            return true;
        }
        return startPoint.isSameRow(targetPoint) || startPoint.isSameColumn(targetPoint);
    }

    @Override
    public Route searchRoute(Point startPoint, Point targetPoint) {
        List<Point> route = new ArrayList<>();
        Direction direction = Direction.calculateDirections(startPoint, targetPoint);
        PointDistance distance = PointDistance.calculate(startPoint, targetPoint);

        Point pointer = startPoint;
        for (int i = 0; i < (int) distance.getDistance() - 1; i++) {
            pointer = direction.move(pointer);
            route.add(pointer);
        }
        return new Route(route, targetPoint);
    }
}
