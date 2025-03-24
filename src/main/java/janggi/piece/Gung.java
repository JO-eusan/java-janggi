package janggi.piece;

import janggi.game.Team;
import janggi.point.PalacePoints;
import janggi.point.Point;
import janggi.point.PointDistance;
import janggi.point.Route;
import java.util.List;

public class Gung implements Movable {

    private static final String NAME = "궁";
    private static final double MOVE_DISTANCE = Math.sqrt(2);

    private final Team team;

    public Gung(Team team) {
        this.team = team;
    }

    @Override
    public boolean isInMovingRange(Point startPoint, Point targetPoint) {
        PointDistance distance = PointDistance.calculate(startPoint, targetPoint);

        return distance.isLessAndEqualTo(MOVE_DISTANCE)
            && PalacePoints.isInPalaceRange(team, targetPoint);
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
