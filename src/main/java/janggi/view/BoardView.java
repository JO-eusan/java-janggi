package janggi.view;

import janggi.game.Board;
import janggi.game.team.Team;
import janggi.game.team.TeamScore;
import janggi.piece.Movable;
import janggi.piece.pieces.RunningPieces;
import janggi.point.Point;
import java.util.Arrays;
import java.util.Map;

public class BoardView {

    public static final String GREY_COLOR_CODE = "\u001B[37m";
    public static final String EXIT_COLOR_CODE = "\u001B[0m";
    private static final String EMPTY_CELL = "\u001B[30m" + (char) 0x3000 + "\u001B[0m";
    private static final String PALACE_CELL = "\u001B[33m" + (char) 0xFF30 + "\u001B[0m";
    private static final int ROW_SIZE = 10;
    private static final int COLUMN_SIZE = 9;

    private final String[][] matrix = new String[ROW_SIZE][COLUMN_SIZE];

    public BoardView() {
        clearBoard();
    }

    public void printTeam(Team team) {
        System.out.println();
        System.out.printf("%s의 차례입니다.%n", team.getColorCode() + team.getText() + EXIT_COLOR_CODE);
    }

    public void printDuration(int duration) {
        System.out.println();
        System.out.printf("경과 시간: %d분", duration);
        System.out.println();
    }

    public void printMovingResult(RunningPieces pieces, Point startPoint, Point targetPoint) {
        Movable piece = pieces.findPieceByPoint(targetPoint);

        System.out.println();
        System.out.printf("%s를(을) (%d, %d) -> (%d, %d)로 이동했습니다.%n", piece.getName(),
            startPoint.row(), startPoint.column(),
            targetPoint.row(), targetPoint.column());
    }

    public void displayScoreBoard(TeamScore score, Team winner) {
        System.out.printf("%n%s가 승리하였습니다.%n",
            winner.getColorCode() + winner.getText() + EXIT_COLOR_CODE);

        System.out.println("----------------");
        System.out.println("<점수판>");
        System.out.printf("- 초나라: %.1f%n", score.findScoreByTeam(Team.CHO));
        System.out.printf("- 한나라: %.1f%n", score.findScoreByTeam(Team.HAN));
    }

    public void displayBoard(Board board) {
        clearBoard();
        placePieces(board.getRunningPieces().getRunningPieces());

        for (int row = 0; row < ROW_SIZE; row++) {
            System.out.println(buildRow(row));
        }
        System.out.println(buildColumnHeaders());
    }

    private void clearBoard() {
        for (String[] row : matrix) {
            Arrays.fill(row, EMPTY_CELL);
        }

        markPalaceBorder(0, 3);
        markPalaceBorder(7, 3);
    }

    private void markPalaceBorder(int startRow, int startColumn) {
        for (int row = startRow; row < startRow + 3; row++) {
            for (int col = startColumn; col < startColumn + 3; col++) {
                matrix[row][col] = PALACE_CELL;
            }
        }
    }

    private void placePieces(Map<Point, Movable> pieces) {
        for (Map.Entry<Point, Movable> entry : pieces.entrySet()) {
            Point point = entry.getKey();
            Movable piece = entry.getValue();
            matrix[point.row()][point.column()] = formatPiece(piece);
        }
    }

    private String formatPiece(Movable piece) {
        return piece.getTeam().getColorCode() + piece.getName() + EXIT_COLOR_CODE;
    }

    private String buildRow(int row) {
        StringBuilder rowBuilder = new StringBuilder();
        rowBuilder.append(
            String.format(" %2s |", GREY_COLOR_CODE + toFullWidthNumber(row) + EXIT_COLOR_CODE));
        for (String token : matrix[row]) {
            rowBuilder.append(String.format(" %2s ", token));
        }
        return rowBuilder.toString();
    }

    private String buildColumnHeaders() {
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append(" " + EMPTY_CELL + " |");
        for (int column = 0; column < COLUMN_SIZE; column++) {
            headerBuilder.append(
                String.format(" %2s ",
                    GREY_COLOR_CODE + toFullWidthNumber(column) + EXIT_COLOR_CODE));
        }
        return headerBuilder.toString();
    }

    private String toFullWidthNumber(int number) {
        String numberStr = String.valueOf(number);
        StringBuilder sb = new StringBuilder();
        for (char c : numberStr.toCharArray()) {
            sb.append((char) (c - '0' + '０'));
        }
        return sb.toString();
    }
}
