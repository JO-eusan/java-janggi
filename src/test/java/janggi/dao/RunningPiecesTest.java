package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.dao.connector.MySQLConnector;
import janggi.game.Board;
import janggi.game.team.Team;
import janggi.piece.pieces.InitialPieces;
import janggi.piece.pieces.RunningPieces;
import janggi.point.Point;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RunningPiecesTest {

    private static final String TEST_BOARD_NAME = "testBoard";

    private Board board;
    private RunningPieces runningPieces;
    private BoardDao boardDao = new BoardDao(MySQLConnector.createConnection());
    private RunningPiecesDao runningPiecesDao = new RunningPiecesDao(
        MySQLConnector.createConnection());

    @BeforeEach
    void initTestBoard() {
        runningPieces = InitialPieces.getAllPieces();
        board = new Board(runningPieces, Team.CHO, TEST_BOARD_NAME);

        boardDao.saveBoard(board, LocalTime.now());
    }

    @AfterEach
    void deleteTestBoard() {
        runningPiecesDao.deleteByBoardName(TEST_BOARD_NAME);
        boardDao.deleteByBoardName(TEST_BOARD_NAME);
    }

    @Test
    @DisplayName("기물 저장 테스트")
    public void savePieces() {
        runningPiecesDao.saveRunningPieces(TEST_BOARD_NAME, runningPieces);

        assertThat(runningPiecesDao.findByBoardName(TEST_BOARD_NAME))
            .isInstanceOf(RunningPieces.class);
    }

    @Test
    @DisplayName("전체 기물 검색 테스트")
    public void findPieces() {
        runningPiecesDao.saveRunningPieces(TEST_BOARD_NAME, runningPieces);

        assertThat(runningPiecesDao.findByBoardName(TEST_BOARD_NAME))
            .isInstanceOf(RunningPieces.class);
    }

    @Test
    @DisplayName("위치 업데이트 테스트")
    public void updatePoint() {
        runningPiecesDao.saveRunningPieces(TEST_BOARD_NAME, runningPieces);

        Point startPoint = new Point(6, 5);
        Point targetPoint = new Point(6, 6);

        runningPiecesDao.updatePoint(TEST_BOARD_NAME, runningPieces, startPoint, targetPoint);

        assertThat(runningPiecesDao.existByPoint(TEST_BOARD_NAME, targetPoint)).isTrue();
    }

    @Test
    @DisplayName("존재하는 특정 기물 삭제 테스트")
    public void deletePiece() {
        runningPiecesDao.saveRunningPieces(TEST_BOARD_NAME, runningPieces);

        Point targetPoint = new Point(6, 6);

        runningPiecesDao.deletePiece(TEST_BOARD_NAME, targetPoint);

        assertThat(runningPiecesDao.existByPoint(TEST_BOARD_NAME, targetPoint)).isFalse();
    }
}
