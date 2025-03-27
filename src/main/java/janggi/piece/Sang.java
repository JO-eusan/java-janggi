package janggi.piece;

import janggi.game.Team;
import janggi.point.Direction;
import janggi.point.Point;
import janggi.point.PointDistance;
import janggi.point.Route;
import java.util.ArrayList;
import java.util.List;

public class Sang implements Movable {

    private static final double MOVE_DISTANCE = Math.sqrt(13);
    private static final int DIAGONAL_COUNT = 2;

    private final PieceType type;
    private final Team team;

    public Sang(Team team) {
        this.type = PieceType.SANG;
        this.team = team;
    }

    @Override
    public boolean isInMovingRange(Point startPoint, Point targetPoint) {
        PointDistance distance = PointDistance.calculate(startPoint, targetPoint);

        return distance.isSameWith(MOVE_DISTANCE);
    }

    @Override
    public Route findRoute(Point startPoint, Point targetPoint) {
        List<Point> route = new ArrayList<>();
        List<Direction> directions = Direction.calculateDirections(startPoint, targetPoint, DIAGONAL_COUNT);

        Point pointer = startPoint;
        for (Direction direction : directions) {
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
