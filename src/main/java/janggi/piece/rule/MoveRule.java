package janggi.piece.rule;

import janggi.point.Point;
import janggi.point.Route;

public interface MoveRule {

    boolean canMove(Point startPoint, Point targetPoint);

    Route searchRoute(Point startPoint, Point targetPoint);
}
