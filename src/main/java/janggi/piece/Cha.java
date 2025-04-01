package janggi.piece;

import janggi.game.team.Team;
import janggi.piece.rule.MoveRule;
import janggi.piece.rule.UnboundDistanceMoveRule;
import janggi.piece.type.PieceType;
import janggi.point.Point;
import janggi.point.Route;

public class Cha implements Movable {

    private final PieceType type;
    private final MoveRule rule;
    private final Team team;

    public Cha(Team team) {
        this.type = PieceType.CHA;
        this.rule = new UnboundDistanceMoveRule();
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
        return type.getScore() + team.getExtraScore();
    }
}
