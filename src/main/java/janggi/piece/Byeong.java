package janggi.piece;

import janggi.game.team.Team;
import janggi.piece.rule.MoveRule;
import janggi.piece.rule.TeamBoundMoveRule;
import janggi.piece.type.PieceType;
import janggi.point.Point;
import janggi.point.Route;

public class Byeong implements Movable {

    private static final double MOVE_DISTANCE_OUT_PALACE = 1;
    private static final double MOVE_DISTANCE_IN_PALACE = Math.sqrt(2);

    private final PieceType type;
    private final MoveRule rule;
    private final Team team;

    public Byeong(Team team) {
        this.type = PieceType.BYEONG;
        this.rule = new TeamBoundMoveRule(MOVE_DISTANCE_OUT_PALACE, MOVE_DISTANCE_IN_PALACE, team);
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
