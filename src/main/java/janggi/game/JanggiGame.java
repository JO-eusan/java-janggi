package janggi.game;

import janggi.game.team.Team;
import janggi.game.team.TeamScore;
import janggi.point.Point;
import janggi.view.BoardView;
import janggi.view.InputView;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class JanggiGame {

    private static final String WINNING_DECISION_TARGET = "궁";
    private static final int WINNING_DECISION_TARGET_COUNT = 2;
    private static final int WINNING_DECISION_TIME_COUNT = 1;

    private final InputView inputView;
    private final BoardView boardView;

    public JanggiGame(InputView inputView, BoardView boardView) {
        this.inputView = inputView;
        this.boardView = boardView;
    }

    public void start() {
        if (inputView.readGameStart()) {
            Board board = Board.putPiecesOnPoint(Team.CHO);
            boardView.displayBoard(board);

            enterGame(board);
            exitGame(board);
        }
    }

    private void enterGame(Board board) {
        Instant startTime = Instant.now();

        while (board.countPieces(WINNING_DECISION_TARGET) == WINNING_DECISION_TARGET_COUNT) {
            boardView.printTeam(board.getTurn());
            transferPiece(board);

            int duration = (int) Duration.between(startTime, Instant.now()).toMinutes();
            if (duration < WINNING_DECISION_TIME_COUNT) {
                break;
            }
            boardView.printDuration(duration);
        }
    }

    private void transferPiece(Board board) {
        handleMoveException(() -> {
            List<Point> startAndTargetPoint = inputView.readStartAndTargetPoint();
            Point startPoint = startAndTargetPoint.getFirst();
            Point targetPoint = startAndTargetPoint.getLast();

            board.move(startPoint, targetPoint);
            boardView.printMovingResult(board.getRunningPieces(), startPoint, targetPoint);
            boardView.displayBoard(board);

            board.reverseTurn();
        });
    }

    private void exitGame(Board board) {
        TeamScore score = board.calculateScoreOfAllTeam();
        boardView.displayScoreBoard(score, board.decideWinner(WINNING_DECISION_TARGET));
    }

    private void handleMoveException(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
