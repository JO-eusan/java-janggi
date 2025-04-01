package janggi.piece.type;

import janggi.game.team.Team;
import janggi.piece.Byeong;
import janggi.piece.Cha;
import janggi.piece.Gung;
import janggi.piece.Ma;
import janggi.piece.Movable;
import janggi.piece.Po;
import janggi.piece.Sa;
import janggi.piece.Sang;
import java.util.Arrays;
import java.util.Map;

public enum PieceType {
    GUNG("궁", 0.0),
    SA("사", 3.0),
    CHA("차", 13.0),
    PO("포", 7.0),
    MA("마", 5.0),
    SANG("상", 3.0),
    BYEONG("병", 2.0);

    private final String text;
    private final double score;

    PieceType(String text, double score) {
        this.text = text;
        this.score = score;
    }

    public static PieceType of(String text) {
        return Arrays.stream(values())
            .filter(type -> text.equals(type.getText()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 기물이 존재하지 않습니다."));
    }

    public static Movable createPieceBy(PieceType type, Team team) {
        Map<PieceType, Movable> pieceCreators = Map.of(
            PieceType.GUNG, new Gung(team),
            PieceType.SA, new Sa(team),
            PieceType.CHA, new Cha(team),
            PieceType.PO, new Po(team),
            PieceType.MA, new Ma(team),
            PieceType.SANG, new Sang(team),
            PieceType.BYEONG, new Byeong(team)
        );
        return pieceCreators.get(type);
    }

    public String getText() {
        return text;
    }

    public double getScore() {
        return score;
    }
}
