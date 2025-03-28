package janggi.game.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.dao.BoardDao;
import janggi.game.Board;
import janggi.game.team.Team;
import janggi.piece.Byeong;
import janggi.piece.Movable;
import janggi.piece.pieces.RunningPieces;
import janggi.point.Point;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardDaoTest {

    private final BoardDao boardDao = new BoardDao();

    @Test
    @DisplayName("데이터베이스 연결 테스트")
    void connection() {
        assertThat(boardDao.getConnection()).isNotNull();
    }

    @Test
    @DisplayName("보드 저장 테스트")
    public void saveBoard() {
        Map<Point, Movable> pieces = new HashMap<>(Map.of(
            new Point(6, 4), new Byeong(Team.CHO)
        ));
        RunningPieces runningPieces = new RunningPieces(pieces);
        Board board = new Board(runningPieces, Team.CHO, "testBoard");

        assertThatCode(() -> boardDao.saveBoard(board));
    }
}
