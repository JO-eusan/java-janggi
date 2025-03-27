package janggi.piece;

import janggi.game.team.Team;
import janggi.piece.type.PieceType;
import janggi.point.PalacePoints;
import janggi.point.Point;
import janggi.point.PointDistance;
import janggi.point.Route;
import java.util.List;

public class Gung implements Movable {

    private static final double CARDINAL_MOVE_DISTANCE = 1;
    private static final double DIAGONAL_MOVE_DISTANCE = Math.sqrt(2);

    private final PieceType type;
    private final Team team;

    public Gung(Team team) {
        this.type = PieceType.GUNG;
        this.team = team;
    }

    @Override
    public boolean isInMovingRange(Point startPoint, Point targetPoint) {
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
    public Route findRoute(Point startPoint, Point targetPoint) {
        return new Route(List.of(), targetPoint);
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
        return type.getScore();
    }
}
