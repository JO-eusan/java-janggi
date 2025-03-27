package janggi.piece;

import janggi.game.Team;
import janggi.point.Point;
import janggi.point.Route;

public interface Movable {

    boolean isInMovingRange(Point startPoint, Point targetPoint);

    Route findRoute(Point startPoint, Point targetPoint);

    Team getTeam();

    String getName();

    double getScore(Team team);
}
