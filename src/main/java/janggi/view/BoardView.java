package janggi.view;

import janggi.game.Board;
import janggi.game.Team;
import janggi.piece.Movable;
import janggi.point.Point;
import java.util.Arrays;
import java.util.Map;

public class BoardView {

    public static final String GREY_COLOR_CODE = "\u001B[37m";
    public static final String EXIT_COLOR_CODE = "\u001B[0m";
    private static final String EMPTY_CELL = "\u001B[30m" + (char) 0x3000 + "\u001B[0m";
    private static final int ROW_SIZE = 10;
    private static final int COLUMN_SIZE = 9;

    private final String[][] matrix = new String[ROW_SIZE][COLUMN_SIZE];

    public BoardView() {
        clearBoard();
    }

    public void printTeam(Team team) {
        System.out.println();
        System.out.printf("%s의 차례입니다.%n", team.getText());
    }

    public void printMovingResult(Board board, Point startPoint, Point targetPoint) {
        Movable piece = board.findPieceByPoint(targetPoint);

        System.out.println();
        System.out.printf("%s를(을) (%d, %d) -> (%d, %d)로 이동했습니다.%n", piece.getName(),
            startPoint.row(), startPoint.column(),
            targetPoint.row(), targetPoint.column());
    }

    public void displayBoard(Board board) {
        clearBoard();
        placePieces(board.getRunningPieces());

        for (int row = 0; row < ROW_SIZE; row++) {
            System.out.println(buildRow(row));
        }
        System.out.println(buildColumnHeaders());
    }

    private void clearBoard() {
        for (String[] row : matrix) {
            Arrays.fill(row, EMPTY_CELL);
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
