package janggi.piece;

import janggi.game.Team;
import janggi.point.Point;
import janggi.point.PointDistance;
import janggi.point.Route;
import java.util.List;

public class Sa implements Movable {

    private static final String NAME = "사";
    private static final double MOVE_DISTANCE = 1;

    private final Team team;

    public Sa(Team team) {
        this.team = team;
    }

    @Override
    public boolean isInMovingRange(Point startPoint, Point targetPoint) {
        PointDistance distance = PointDistance.calculate(startPoint, targetPoint);

        return distance.isLessAndEqualTo(MOVE_DISTANCE);
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
    public Team getTeam() {
        return this.team;
    }
}
