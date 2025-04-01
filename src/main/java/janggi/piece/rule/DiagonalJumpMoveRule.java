package janggi.piece.rule;

import janggi.point.Direction;
import janggi.point.Point;
import janggi.point.PointDistance;
import janggi.point.Route;
import java.util.ArrayList;
import java.util.List;

public class DiagonalJumpMoveRule implements MoveRule{

    private final double moveDistance;
    private final int diagonalCount;

    public DiagonalJumpMoveRule(double moveDistance, int diagonalCount) {
        this.moveDistance = moveDistance;
        this.diagonalCount = diagonalCount;
    }

    @Override
    public boolean canMove(Point startPoint, Point targetPoint) {
        PointDistance distance = PointDistance.calculate(startPoint, targetPoint);

        return distance.isSameWith(moveDistance);
    }

    @Override
    public Route searchRoute(Point startPoint, Point targetPoint) {
        List<Point> route = new ArrayList<>();
        List<Direction> directions = Direction.calculateDirections(startPoint, targetPoint, diagonalCount);

        Point pointer = startPoint;
        for (Direction direction : directions) {
            pointer = direction.move(pointer);
            route.add(pointer);
        }
        return new Route(route, targetPoint);
    }
}
