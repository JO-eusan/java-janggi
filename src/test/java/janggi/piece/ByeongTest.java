package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.game.Team;
import janggi.point.Point;
import janggi.point.Route;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ByeongTest {

    @Nested
    @DisplayName("이동 가능 확인 테스트")
    class CheckMovableTest {

        @Test
        @DisplayName("좌로 이동할 수 있다면 true를 반환한다.")
        void checkLeftMovable() {
            Byeong byeong = new Byeong(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(6, 5);

            assertThat(byeong.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("우로 이동할 수 있다면 true를 반환한다.")
        void checkRightMovable() {
            Byeong byeong = new Byeong(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(6, 7);

            assertThat(byeong.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("초나라의 경우 북쪽으로 이동할 수 있다면 true를 반환한다.")
        void checkUpMovableWithCho() {
            Byeong byeong = new Byeong(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(5, 6);

            assertThat(byeong.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("초나라의 경우 남쪽으로 이동할 수 있다면 false를 반환한다.")
        void checkDownMovableWithCho() {
            Byeong byeong = new Byeong(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(7, 6);

            assertThat(byeong.isInMovingRange(startPoint, targetPoint)).isFalse();
        }

        @Test
        @DisplayName("한나라의 경우 북쪽으로 이동할 수 있다면 false를 반환한다.")
        void checkUpMovableWithHan() {
            Byeong byeong = new Byeong(Team.HAN);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(5, 6);

            assertThat(byeong.isInMovingRange(startPoint, targetPoint)).isFalse();
        }

        @Test
        @DisplayName("한나라의 경우 남쪽으로 이동할 수 있다면 true를 반환한다.")
        void checkDownMovableWithHan() {
            Byeong byeong = new Byeong(Team.HAN);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(7, 6);

            assertThat(byeong.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @ParameterizedTest
        @MethodSource("getStartAndTargetPointsInPalace")
        @DisplayName("궁성에서 대각선이 이동 가능한 경우 true를 반환한다.")
        void checkMovableInPalace(Point startPoint, Point targetPoint) {
            Byeong byeong = new Byeong(Team.CHO);

            assertThat(byeong.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        private static Stream<Arguments> getStartAndTargetPointsInPalace() {
            return Stream.of(
                Arguments.arguments(new Point(8, 4), new Point(7, 3)),
                Arguments.arguments(new Point(8, 4), new Point(7, 5)),
                Arguments.arguments(new Point(2, 5), new Point(1, 4)),
                Arguments.arguments(new Point(2, 3), new Point(1, 4))
            );
        }

        @ParameterizedTest
        @MethodSource("getStartAndTargetPointsOutPalace")
        @DisplayName("궁성에서 대각선이 이동 불가능한 경우 false를 반환한다.")
        void checkMovableOutPalace(Point startPoint, Point targetPoint) {
            Byeong byeong = new Byeong(Team.CHO);

            assertThat(byeong.isInMovingRange(startPoint, targetPoint)).isFalse();
        }

        private static Stream<Arguments> getStartAndTargetPointsOutPalace() {
            return Stream.of(
                Arguments.arguments(new Point(8, 4), new Point(9, 3)),
                Arguments.arguments(new Point(8, 4), new Point(9, 5)),
                Arguments.arguments(new Point(2, 4), new Point(1, 3)),
                Arguments.arguments(new Point(2, 4), new Point(1, 5))
            );
        }
    }

    @Nested
    @DisplayName("경로 테스트")
    class RouteTest {

        @Test
        @DisplayName("좌로 이동 경로를 생성할 수 있다.")
        void checkLeftRouteMovable() {
            Byeong byeong = new Byeong(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(6, 5);

            Route route = byeong.findRoute(startPoint, targetPoint);

            assertAll(() -> {
                assertThat(route.getPath()).hasSize(0);
                assertThat(route.getTargetPoint()).isEqualTo(new Point(6, 5));
            });
        }

        @Test
        @DisplayName("우로 이동 경로를 생성할 수 있다.")
        void checkRightRouteMovable() {
            Byeong byeong = new Byeong(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(6, 7);

            Route route = byeong.findRoute(startPoint, targetPoint);

            assertAll(() -> {
                assertThat(route.getPath()).hasSize(0);
                assertThat(route.getTargetPoint()).isEqualTo(new Point(6, 7));
            });
        }

        @Test
        @DisplayName("상로 이동 경로를 생성할 수 있다.")
        void checkUpRouteMovable() {
            Byeong byeong = new Byeong(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(5, 6);

            Route route = byeong.findRoute(startPoint, targetPoint);

            assertAll(() -> {
                assertThat(route.getPath()).hasSize(0);
                assertThat(route.getTargetPoint()).isEqualTo(new Point(5, 6));
            });
        }
    }
}
