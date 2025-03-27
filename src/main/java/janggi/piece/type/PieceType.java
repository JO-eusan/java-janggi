package janggi.piece.type;

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

    public String getText() {
        return text;
    }

    public double getScore() {
        return score;
    }
}
