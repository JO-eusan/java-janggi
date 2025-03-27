package janggi.game;

import janggi.point.Point;
import janggi.view.BoardView;
import janggi.view.InputView;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class JanggiApplication {

    private static final String WINNING_DECISION_TARGET = "궁";
    private static final int WINNING_DECISION_TARGET_COUNT = 2;
    private static final int WINNING_DECISION_TIME_COUNT = 1;

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
            TeamScore score = board.calculateScoreOfAllTeam();
            boardView.displayScoreBoard(score, board.decideWinner(WINNING_DECISION_TARGET));
        }
    }

    private void processGame(Board board) {
        Instant startTime = Instant.now();
        int duration;
        do {
            boardView.printTeam(board.getTurn());
            handleMoveException(() -> {
                List<Point> startAndTargetPoint = inputView.readStartAndTargetPoint();
                Point startPoint = startAndTargetPoint.getFirst();
                Point targetPoint = startAndTargetPoint.getLast();

                board.move(startPoint, targetPoint);
                boardView.printMovingResult(board.getRunningPieces(), startPoint, targetPoint);
                boardView.displayBoard(board);

                board.reverseTurn();
            });

            duration = (int) Duration.between(startTime, Instant.now()).toMinutes();
            boardView.printDuration(duration);
        } while ((duration < WINNING_DECISION_TIME_COUNT)
            && (board.countPieces(WINNING_DECISION_TARGET) == WINNING_DECISION_TARGET_COUNT));
    }

    private void handleMoveException(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
