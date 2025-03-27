package janggi.piece;

import janggi.game.team.Team;
import janggi.piece.type.PieceType;
import janggi.point.Direction;
import janggi.point.PalacePoints;
import janggi.point.Point;
import janggi.point.PointDistance;
import janggi.point.Route;
import java.util.ArrayList;
import java.util.List;

public class Po implements Movable {

    private final PieceType type;
    private final Team team;

    public Po(Team team) {
        this.type = PieceType.PO;
        this.team = team;
    }

    @Override
    public boolean isInMovingRange(Point startPoint, Point targetPoint) {
        if (PalacePoints.isInPalaceWithMovableDiagonal(startPoint)
            && PalacePoints.isInPalaceWithMovableDiagonal(targetPoint)) {
            return true;
        }
        return startPoint.isSameRow(targetPoint) || startPoint.isSameColumn(targetPoint);
    }

    @Override
    public Route findRoute(Point startPoint, Point targetPoint) {
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
