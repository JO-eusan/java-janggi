package janggi.piece.pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.game.team.Team;
import janggi.piece.Byeong;
import janggi.piece.Cha;
import janggi.piece.Gung;
import janggi.piece.Ma;
import janggi.piece.Movable;
import janggi.piece.Po;
import janggi.point.Point;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class RunningPiecesTest {

    @Nested
    @DisplayName("기물 검색 테스트")
    class SearchTest {

        @Test
        @DisplayName("이름으로 기물을 찾을 수 있다.")
        void findPiecesByName() {
            Map<Point, Movable> pieces = Map.of(
                new Point(6, 4), new Byeong(Team.CHO)
            );
            RunningPieces runningPieces = new RunningPieces(pieces);

            assertThat(runningPieces.findPiecesByName("병")).hasSize(1);
        }

        @Test
        @DisplayName("좌표로 기물을 찾을 수 있다.")
        void findPieceByPoint() {
            Map<Point, Movable> pieces = Map.of(
                new Point(6, 4), new Byeong(Team.CHO)
            );
            RunningPieces runningPieces = new RunningPieces(pieces);

            assertThat(runningPieces.findPieceByPoint(new Point(6, 4))).isInstanceOf(Byeong.class);
        }

        @Test
        @DisplayName("좌표로 기물이 존재하면 true를 반환한다.")
        void existPiece() {
            Map<Point, Movable> pieces = Map.of(
                new Point(6, 4), new Byeong(Team.CHO)
            );
            RunningPieces runningPieces = new RunningPieces(pieces);

            assertThat(runningPieces.existPieceOnPoint(new Point(6, 4))).isTrue();
        }

        @Test
        @DisplayName("좌표로 기물이 존재하지 않으면 false를 반환한다.")
        void notExistPiece() {
            Map<Point, Movable> pieces = Map.of(
                new Point(6, 4), new Byeong(Team.CHO)
            );
            RunningPieces runningPieces = new RunningPieces(pieces);

            assertThat(runningPieces.existPieceOnPoint(new Point(5, 3))).isFalse();
        }
    }

    @Nested
    @DisplayName("기본 이동 검증 테스트")
    class MovableExceptPoTest {

        @Test
        @DisplayName("기물의 이동 범위를 벗어나면 이동 시킬 수 없다.")
        void notMovePieceOutOfRange() {
            Map<Point, Movable> pieces = new HashMap<>(
                Map.of(new Point(6, 4), new Byeong(Team.CHO)));
            RunningPieces runningPieces = new RunningPieces(pieces);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(4, 4);

            assertThatThrownBy(() -> runningPieces.validateMovable(startPoint, targetPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치로 이동할 수 없습니다.");
        }

        @Test
        @DisplayName("기물은 이동 경로에 다른 기물이 있으면 이동 시킬 수 없다.")
        void notMovePieceWithHurdle() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Ma(Team.CHO),
                new Point(5, 4), new Byeong(Team.CHO)
            ));
            RunningPieces runningPieces = new RunningPieces(pieces);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(4, 5);

            assertThatThrownBy(() -> runningPieces.validateMovable(startPoint, targetPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치로 이동할 수 없습니다.");
        }

        @Test
        @DisplayName("이동 하는 위치에 같은 팀의 기물이 있으면 공격할 수 없다.")
        void notAttackPiece() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Byeong(Team.CHO),
                new Point(5, 4), new Byeong(Team.CHO)
            ));
            RunningPieces runningPieces = new RunningPieces(pieces);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(5, 4);

            assertThatThrownBy(() -> runningPieces.validateMovable(startPoint, targetPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치로 이동할 수 없습니다.");
        }
    }

    @Nested
    @DisplayName("포 이동 검증 테스트")
    class PoMovableTest {

        @Test
        @DisplayName("기물의 이동 범위를 벗어나면 이동 시킬 수 없다.")
        void notMovePieceOutOfRange() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(new Point(6, 4), new Po(Team.CHO)));
            RunningPieces runningPieces = new RunningPieces(pieces);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(5, 3);

            assertThatThrownBy(() -> runningPieces.validateMovable(startPoint, targetPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치로 이동할 수 없습니다.");
        }

        @Test
        @DisplayName("이동 하는 위치에 같은 팀의 기물이 있으면 공격할 수 없다.")
        void notAttackPiece() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Po(Team.CHO),
                new Point(5, 4), new Byeong(Team.CHO)
            ));
            RunningPieces runningPieces = new RunningPieces(pieces);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(5, 4);

            assertThatThrownBy(() -> runningPieces.validateMovable(startPoint, targetPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치로 이동할 수 없습니다.");
        }

        @Test
        @DisplayName("포는 포를 제외한 기물이 경로에 하나가 없다면 공격할 수 없다.")
        void notAttackPieceWithoutHurdle() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Po(Team.CHO),
                new Point(5, 4), new Po(Team.HAN),
                new Point(4, 4), new Byeong(Team.HAN)
            ));
            RunningPieces runningPieces = new RunningPieces(pieces);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(4, 4);

            assertThatThrownBy(() -> runningPieces.validateMovable(startPoint, targetPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("포는 포를 제외한 하나의 기물만 필요합니다.");
        }

        @Test
        @DisplayName("포는 포를 공격할 수 없다.")
        void notAttackPieceWithPo() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Po(Team.CHO),
                new Point(5, 4), new Byeong(Team.CHO),
                new Point(4, 4), new Po(Team.HAN)
            ));
            RunningPieces runningPieces = new RunningPieces(pieces);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(4, 4);

            assertThatThrownBy(() -> runningPieces.validateMovable(startPoint, targetPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("포는 포를 잡을 수 없습니다.");
        }
    }

    @Nested
    @DisplayName("기물 위치 변경 테스트")
    class ChangePieceTest {

        @Test
        @DisplayName("기물을 이동시킬 수 있다.")
        void updatePiece() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Po(Team.CHO)
            ));
            RunningPieces runningPieces = new RunningPieces(pieces);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(4, 4);

            runningPieces.updatePiece(startPoint, targetPoint);

            assertAll(() -> {
                assertThatThrownBy(() -> runningPieces.findPieceByPoint(startPoint))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("해당 좌표에 기물이 존재하지 않습니다.");
                assertThat(runningPieces.findPieceByPoint(targetPoint)).isInstanceOf(Movable.class);
            });
        }
    }

    @Nested
    @DisplayName("점수 테스트")
    class ScoreTest {

        @Test
        @DisplayName("남은 기물의 점수를 계산할 수 있다.")
        void calculateScoreOfAllTeam() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Po(Team.CHO), // 7점
                new Point(5, 4), new Byeong(Team.CHO), // 2점
                new Point(3, 4), new Ma(Team.CHO), // 5점
                new Point(4, 4), new Cha(Team.HAN), // 13 + 1.5점
                new Point(6, 6), new Gung(Team.HAN) // 0점
            ));
            RunningPieces runningPieces = new RunningPieces(pieces);

            assertAll(() -> {
                assertThat(runningPieces.calculateTotalScore(Team.CHO)).isEqualTo(14);
                assertThat(runningPieces.calculateTotalScore(Team.HAN)).isEqualTo(14.5);
            });
        }
    }
}
