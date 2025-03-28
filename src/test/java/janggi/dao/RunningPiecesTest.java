package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.dao.RunningPiecesDao;
import janggi.piece.pieces.InitialPieces;
import janggi.piece.pieces.RunningPieces;
import janggi.point.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RunningPiecesTest {

    private RunningPieces runningPieces;
    private final RunningPiecesDao runningPiecesDao = new RunningPiecesDao();

    @BeforeEach
    void initTestBoard() {
        runningPieces = InitialPieces.getAllPieces();
        runningPiecesDao.deleteAllPieces();
        runningPiecesDao.saveRunningPieces("testBoard", runningPieces);
    }

    @Test
    @DisplayName("데이터베이스 연결 테스트")
    void connection() {
        assertThat(runningPiecesDao.getConnection()).isNotNull();
    }

    @Test
    @DisplayName("기물 저장 테스트")
    public void savePieces() {
        RunningPieces newRunningPieces = InitialPieces.getAllPieces();

        runningPiecesDao.deleteAllPieces();
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
