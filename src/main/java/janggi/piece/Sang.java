package janggi.piece;

import janggi.game.team.Team;
import janggi.piece.rule.DiagonalJumpMoveRule;
import janggi.piece.rule.MoveRule;
import janggi.piece.type.PieceType;
import janggi.point.Point;
import janggi.point.Route;

public class Sang implements Movable {

    private static final double MOVE_DISTANCE = Math.sqrt(13);
    private static final int DIAGONAL_COUNT = 2;

    private final PieceType type;
    private final MoveRule rule;
    private final Team team;

    public Sang(Team team) {
        this.type = PieceType.SANG;
        this.rule = new DiagonalJumpMoveRule(MOVE_DISTANCE, DIAGONAL_COUNT);
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
