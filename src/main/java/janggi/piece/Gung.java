package janggi.piece;

import janggi.game.team.Team;
import janggi.piece.rule.MoveRule;
import janggi.piece.rule.PalaceMoveRule;
import janggi.piece.type.PieceType;
import janggi.point.Point;
import janggi.point.Route;

public class Gung implements Movable {

    private final PieceType type;
    private final MoveRule rule;
    private final Team team;

    public Gung(Team team) {
        this.type = PieceType.GUNG;
        this.rule = new PalaceMoveRule(team);
        this.team = team;
    }

    @Override
    public boolean isInMovingRange(Point startPoint, Point targetPoint) {
        return rule.canMove(startPoint, targetPoint);
    }

    @Override
    public Route findRoute(Point startPoint, Point targetPoint) {
        return rule.searchRoute(startPoint, targetPoint);
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
