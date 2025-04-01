package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.dao.connector.MySQLConnector;
import janggi.game.Board;
import janggi.game.team.Team;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardDaoTest {

    private static final String TEST_BOARD_NAME = "testBoard";

    private Board board;
    private BoardDao boardDao = new BoardDao(MySQLConnector.createConnection());

    @BeforeEach
    void initTestBoard() {
        board = Board.putPiecesOnPoint(Team.CHO, TEST_BOARD_NAME);
    }

    @AfterEach
    void deleteTestBoard() {
        boardDao.deleteByBoardName(TEST_BOARD_NAME);
    }

    @Test
    @DisplayName("보드 저장 테스트")
    public void saveBoard() {
        boardDao.saveBoard(board, LocalTime.now());

        assertThat(boardDao.existByBoardName(TEST_BOARD_NAME)).isTrue();
    }

    @Test
    @DisplayName("차례 반환 테스트")
    public void findTurn() {
        boardDao.saveBoard(board, LocalTime.now());

        assertThat(boardDao.findTurnByBoardName(TEST_BOARD_NAME)).isEqualTo(Team.CHO);
    }

    @Test
    @DisplayName("시작 시간 반환 테스트")
    public void findStartTime() {
        boardDao.saveBoard(board, LocalTime.now());

        assertThat(boardDao.findStartTimeByBoardName(TEST_BOARD_NAME)).isInstanceOf(
            LocalTime.class);
    }

    @Test
    @DisplayName("보드 이름 존재 확인 테스트")
    public void existBoard() {
        boardDao.saveBoard(board, LocalTime.now());

        assertThat(boardDao.existByBoardName(TEST_BOARD_NAME)).isTrue();
    }

    @Test
    @DisplayName("차례 업데이트 테스트")
    public void updateTurn() {
        boardDao.saveBoard(board, LocalTime.now());

        board.reverseTurn();
        boardDao.updateTurn(board);

        assertThat(boardDao.findTurnByBoardName(TEST_BOARD_NAME).getText())
            .isEqualTo(Team.HAN.getText());
    }
}
