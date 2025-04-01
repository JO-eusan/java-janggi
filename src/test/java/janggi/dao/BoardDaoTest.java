package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.dao.connector.MySQLConnector;
import janggi.game.Board;
import janggi.game.team.Team;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardDaoTest {

    private Board board;
    private final BoardDao boardDao = new BoardDao(MySQLConnector.createConnection());

    @BeforeEach
    void initTestBoard() {
        board = Board.putPiecesOnPoint(Team.CHO, "testBoard");
        boardDao.deleteAllBoards();
        boardDao.saveBoard(board, LocalTime.now());
    }

    @Test
    @DisplayName("보드 저장 테스트")
    public void saveBoard() {
        boardDao.deleteAllBoards();
        assertThatCode(() -> boardDao.saveBoard(board, LocalTime.now()))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("차례 반환 테스트")
    public void findTurn() {
        String boardName = "testBoard";
        assertThat(boardDao.findTurnByBoardName(boardName)).isEqualTo(Team.CHO);
    }

    @Test
    @DisplayName("시작 시간 반환 테스트")
    public void findStartTime() {
        String boardName = "testBoard";
        assertThat(boardDao.findStartTimeByBoardName(boardName)).isInstanceOf(LocalTime.class);
    }

    @Test
    @DisplayName("보드 이름 존재 확인 테스트")
    public void existBoard() {
        String boardName = "testBoard";
        assertThat(boardDao.existByBoardName(boardName)).isTrue();
    }

    @Test
    @DisplayName("차례 업데이트 테스트")
    public void updateTurn() {
        board.reverseTurn();

        assertThatCode(() -> boardDao.updateTurn(board))
            .doesNotThrowAnyException();
    }
}
