package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

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

    private Board board;
    private RunningPieces runningPieces;
    private final BoardDao boardDao = new BoardDao(MySQLConnector.createConnection());
    private final RunningPiecesDao runningPiecesDao = new RunningPiecesDao(
        MySQLConnector.createConnection());

    @BeforeEach
    void initTestBoard() {
        runningPieces = InitialPieces.getAllPieces();
        board = new Board(runningPieces, Team.CHO, "testBoard");

        boardDao.saveBoard(board, LocalTime.now());
        runningPiecesDao.saveRunningPieces("testBoard", runningPieces);
    }

    @AfterEach
    void deleteTestBoard() {
        runningPiecesDao.deleteByBoardName("testBoard");
        boardDao.deleteByBoardName("testBoard");
    }

    @Test
    @DisplayName("기물 저장 테스트")
    public void savePieces() {
        RunningPieces newRunningPieces = InitialPieces.getAllPieces();

        runningPiecesDao.deleteByBoardName("testBoard");
        assertThatCode(() -> runningPiecesDao.saveRunningPieces("testBoard", newRunningPieces))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("전체 기물 검색 테스트")
    public void findPieces() {
        String boardName = "testBoard";
        assertThat(runningPiecesDao.findByBoardName(boardName)).isInstanceOf(RunningPieces.class);
    }

    @Test
    @DisplayName("위치 업데이트 테스트")
    public void updatePoint() {
        String boardName = "testBoard";
        Point startPoint = new Point(6, 5);
        Point targetPoint = new Point(6, 6);

        assertThatCode(
            () -> runningPiecesDao.updatePoint(boardName, runningPieces, startPoint, targetPoint))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("존재하는 특정 기물 삭제 테스트")
    public void deletePiece() {
        String boardName = "testBoard";
        Point targetPoint = new Point(6, 6);

        assertThatCode(() -> runningPiecesDao.deletePiece(boardName, targetPoint))
            .doesNotThrowAnyException();
    }
}
