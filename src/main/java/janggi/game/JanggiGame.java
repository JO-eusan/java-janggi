package janggi.game;

import janggi.dao.BoardDao;
import janggi.dao.RunningPiecesDao;
import janggi.game.team.Team;
import janggi.game.team.TeamScore;
import janggi.point.Point;
import janggi.view.BoardView;
import janggi.view.InputView;
import java.time.LocalTime;
import java.util.List;

public class JanggiGame {

    private static final String EXIST_BOARD_NAME = "runningBoard";
    private static final String WINNING_DECISION_TARGET = "궁";
    private static final int WINNING_DECISION_TARGET_COUNT = 2;
    private static final int WINNING_DECISION_TIME_COUNT = 15;

    private final InputView inputView;
    private final BoardView boardView;
    private final BoardDao boardDao;
    private final RunningPiecesDao runningPiecesDao;

    public JanggiGame(InputView inputView, BoardView boardView,
        BoardDao boardDao, RunningPiecesDao runningPiecesDao) {
        this.inputView = inputView;
        this.boardView = boardView;
        this.boardDao = boardDao;
        this.runningPiecesDao = runningPiecesDao;
    }

    public void start() {
        if (inputView.readGameStart()) {
            Board board = createBoard();

            enterGame(board);
            exitGame(board);
        }
    }

    private Board createBoard() {
        if (boardDao.existByBoardName(EXIST_BOARD_NAME) && inputView.readGameRestart()) {
            return new Board(runningPiecesDao.findByBoardName(EXIST_BOARD_NAME),
                boardDao.findTurnByBoardName(EXIST_BOARD_NAME), EXIST_BOARD_NAME);
        }
        boardDao.deleteAllBoards();
        runningPiecesDao.deleteAllPieces();

        Board board = Board.putPiecesOnPoint(Team.CHO, EXIST_BOARD_NAME);
        boardDao.saveBoard(board, LocalTime.now().getMinute());
        runningPiecesDao.saveRunningPieces(board.getBoardName(), board.getRunningPieces());

        return board;
    }

    private void enterGame(Board board) {
        boardView.displayBoard(board);

        while (board.countPieces(WINNING_DECISION_TARGET) == WINNING_DECISION_TARGET_COUNT) {
            int duration = LocalTime.now().getMinute() - boardDao.findStartTimeByBoardName(EXIST_BOARD_NAME);
            if (duration > WINNING_DECISION_TIME_COUNT) {
                break;
            }
            boardView.printTeam(board.getTurn());
            transferPiece(board);

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

            boardDao.updateTurn(board);
            runningPiecesDao.deletePiece(board.getBoardName(), targetPoint);
            runningPiecesDao.updatePoint(board.getBoardName(), board.getRunningPieces(),
                startPoint, targetPoint);
        });
    }

    private void exitGame(Board board) {
        TeamScore score = board.calculateScoreOfAllTeam();
        boardView.displayScoreBoard(score, board.decideWinner(WINNING_DECISION_TARGET));

        boardDao.deleteAllBoards();
        runningPiecesDao.deleteAllPieces();
    }

    private void handleMoveException(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
