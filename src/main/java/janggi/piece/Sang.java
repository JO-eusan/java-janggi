package janggi.piece;

import janggi.game.Team;
import janggi.point.Direction;
import janggi.point.Point;
import janggi.point.PointDistance;
import janggi.point.Route;
import java.util.ArrayList;
import java.util.List;

public class Sang implements Movable {

    private static final String NAME = "상";
    private static final double SCORE = 3.0;
    private static final double MOVE_DISTANCE = Math.sqrt(13);
    private static final int DIAGONAL_COUNT = 2;

    private final Team team;

    public Sang(Team team) {
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
        List<Direction> directions = Direction.complexFrom(startPoint, targetPoint, DIAGONAL_COUNT);

        Point pointer = startPoint;
        for (Direction direction : directions) {
            pointer = direction.move(pointer);
            route.add(pointer);
        }
        return new Route(route, targetPoint);
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
