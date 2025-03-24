package janggi.game;

import janggi.point.Point;
import janggi.view.BoardView;
import janggi.view.InputView;
import java.util.List;

public class JanggiApplication {

    private static final String WINNING_DECISION_TARGET = "궁";
    private static final int WINNING_DECISION_TARGET_COUNT = 2;

    private final InputView inputView;
    private final BoardView boardView;

    private JanggiApplication() {
        inputView = new InputView();
        boardView = new BoardView();
    }

    public static void main(String[] args) {
        JanggiApplication janggiApplication = new JanggiApplication();
        janggiApplication.startGame();
    }

    private void startGame() {
        if (inputView.readGameStart()) {
            Board board = Board.putPiecesOnPoint(Team.CHO);
            boardView.displayBoard(board);

            processGame(board);
        }
    }

    private void processGame(Board board) {
        do {
            boardView.printTeam(board.getTurn());

            handleMoveException(() -> {
                List<Point> startAndTargetPoint = inputView.readStartAndTargetPoint();
                Point startPoint = startAndTargetPoint.getFirst();
                Point targetPoint = startAndTargetPoint.getLast();

                board.move(startPoint, targetPoint);
                boardView.printMovingResult(board, startPoint, targetPoint);
                boardView.displayBoard(board);

                board.reverseTurn();
            });
        } while (board.countPieces(WINNING_DECISION_TARGET) == WINNING_DECISION_TARGET_COUNT);
    }

    private void handleMoveException(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
