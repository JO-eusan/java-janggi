package janggi.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.piece.Byeong;
import janggi.piece.Cha;
import janggi.piece.Gung;
import janggi.piece.Ma;
import janggi.piece.Movable;
import janggi.piece.Po;
import janggi.piece.RunningPieces;
import janggi.point.Point;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Nested
    @DisplayName("장기 판 초기화 및 팀 테스트")
    class InitBoardTest {

        @Test
        @DisplayName("32개의 기물을 가진 장기판을 생성할 수 있다.")
        void createBoardWithPieces() {
            Board board = Board.putPiecesOnPoint(Team.CHO);

            RunningPieces pieces = board.getRunningPieces();

            assertThat(pieces.getRunningPieces()).hasSize(32);
        }

        @Test
        @DisplayName("시작 팀을 지정하여 장기판을 생성할 수 있다.")
        void createBoardWithStartTeam() {
            Board board = Board.putPiecesOnPoint(Team.CHO);

            assertThat(board.getTurn()).isEqualTo(Team.CHO);
        }

        @Test
        @DisplayName("팀을 변경할 수 있다.")
        void changeTeam() {
            Board board = Board.putPiecesOnPoint(Team.CHO);

            board.reverseTurn();

            assertThat(board.getTurn()).isEqualTo(Team.HAN);
        }
    }

    @Nested
    @DisplayName("이동 테스트")
    class MoveTest {

        @Test
        @DisplayName("다른 팀의 기물을 이동 시킬 수 없다.")
        void notMovePieceOfSameTeam() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Byeong(Team.HAN)
            ));
            RunningPieces runningPieces = new RunningPieces(pieces);
            Board board = new Board(runningPieces, Team.CHO);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(5, 4);

            assertThatThrownBy(() -> board.move(startPoint, targetPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(board.getTurn().getText() + "의 기물만 이동할 수 있습니다.");
        }

        @Test
        @DisplayName("기물을 이동 시킬 수 있다.")
        void movePiece() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Byeong(Team.CHO)
            ));
            RunningPieces runningPieces = new RunningPieces(pieces);
            Board board = new Board(runningPieces, Team.CHO);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(5, 4);
            board.move(startPoint, targetPoint);

            Map<Point, Movable> updatePieces = runningPieces.getRunningPieces();

            assertThat(updatePieces.get(targetPoint)).isInstanceOf(Movable.class);
        }

        @Test
        @DisplayName("이동 하는 위치에 다른 팀의 기물이 있으면 공격할 수 있다.")
        void attackPiece() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Byeong(Team.CHO),
                new Point(5, 4), new Byeong(Team.HAN)
            ));
            RunningPieces runningPieces = new RunningPieces(pieces);
            Board board = new Board(runningPieces, Team.CHO);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(5, 4);
            board.move(startPoint, targetPoint);

            Map<Point, Movable> updatePieces = runningPieces.getRunningPieces();

            assertAll(() -> {
                assertThat(updatePieces.get(targetPoint)).isInstanceOf(Movable.class);
                assertThat(updatePieces).hasSize(1);
            });
        }
    }

    @Nested
    @DisplayName("승패 테스트")
    class ScoreTest {

        Board board;

        @BeforeEach
        void createBoard() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Po(Team.CHO), // 7점
                new Point(5, 4), new Byeong(Team.CHO), // 2점
                new Point(3, 4), new Ma(Team.CHO), // 5점
                new Point(4, 4), new Cha(Team.HAN), // 13 + 1.5점
                new Point(6, 6), new Gung(Team.HAN) // 0점
            ));
            RunningPieces runningPieces = new RunningPieces(pieces);
            board = new Board(runningPieces, Team.CHO);
        }

        @Test
        @DisplayName("남은 기물의 점수를 계산할 수 있다.")
        void calculateScoreOfAllTeam() {
            TeamScore score = board.calculateScoreOfAllTeam();

            assertAll(() -> {
                assertThat(score.findScoreByTeam(Team.CHO)).isEqualTo(14);
                assertThat(score.findScoreByTeam(Team.HAN)).isEqualTo(14.5);
            });
        }

        @Test
        @DisplayName("승자를 결정할 수 있다.")
        void judgeWinner() {
            Team winner = board.decideWinner("궁");

            assertThat(winner).isEqualTo(Team.HAN);
        }
    }
}
