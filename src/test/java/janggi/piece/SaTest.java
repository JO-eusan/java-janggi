package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.game.team.Team;
import janggi.point.Point;
import janggi.point.Route;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SaTest {

    @Nested
    @DisplayName("이동 가능 확인 테스트")
    class CheckMovableTest {

        @Test
        @DisplayName("궁성 내부에서 좌로 이동할 수 있다면 true를 반환한다.")
        void checkLeftMovableInPalace() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(8, 4);
            Point targetPoint = new Point(8, 3);

            assertThat(sa.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("궁성 내부에서 우로 이동할 수 있다면 true를 반환한다.")
        void checkRightMovableInPalace() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(8, 4);
            Point targetPoint = new Point(8, 5);

            assertThat(sa.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("궁성 내부에서 상으로 이동할 수 있다면 true를 반환한다.")
        void checkUpMovableInPalace() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(8, 4);
            Point targetPoint = new Point(7, 4);

            assertThat(sa.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("궁성 내부에서 하으로 이동할 수 있다면 true를 반환한다.")
        void checkDownMovableInPalace() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(8, 4);
            Point targetPoint = new Point(9, 4);

            assertThat(sa.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("궁성 내부에서 상-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkUpRightMovableInPalace() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(8, 4);
            Point targetPoint = new Point(7, 5);

            assertThat(sa.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("궁성 내부에서 상-좌측대각선로 이동할 수 있다면 true를 반환한다.")
        void checkUpLeftMovableInPalace() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(8, 4);
            Point targetPoint = new Point(7, 3);

            assertThat(sa.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("궁성 내부에서 하-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkDownRightMovableInPalace() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(8, 4);
            Point targetPoint = new Point(9, 5);

            assertThat(sa.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("궁성 내부에서 하-좌측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkDownLeftMovableInPalace() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(8, 4);
            Point targetPoint = new Point(9, 3);

            assertThat(sa.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @ParameterizedTest
        @MethodSource("getStartAndTargetPointsOutPalace")
        @DisplayName("궁성 외부로 벗어난다면 false를 반환한다.")
        void checkMovableOutPalace(Point startPoint, Point targetPoint) {
            Sa sa = new Sa(Team.CHO);

            assertThat(sa.isInMovingRange(startPoint, targetPoint)).isFalse();
        }

        private static Stream<Arguments> getStartAndTargetPointsOutPalace() {
            return Stream.of(
                Arguments.arguments(new Point(7, 4), new Point(6, 4)), // 상
                Arguments.arguments(new Point(8, 3), new Point(8, 2)), // 좌
                Arguments.arguments(new Point(8, 5), new Point(8, 6)), // 우
                Arguments.arguments(new Point(7, 5), new Point(6, 6)), // 상-우측대각선
                Arguments.arguments(new Point(7, 3), new Point(6, 2))  // 상-좌측대각선
            );
        }
    }

    @Nested
    @DisplayName("경로 테스트")
    class RouteTest {

        @Test
        @DisplayName("좌로 이동 경로를 생성할 수 있다.")
        void checkLeftRouteMovable() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(8, 4);
            Point targetPoint = new Point(8, 3);

            Route route = sa.findRoute(startPoint, targetPoint);

            assertAll(() -> {
                assertThat(route.getPath()).hasSize(0);
                assertThat(route.getTargetPoint()).isEqualTo(new Point(8, 3));
            });
        }

        @Test
        @DisplayName("우로 이동 경로를 생성할 수 있다.")
        void checkRightRouteMovable() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(8, 4);
            Point targetPoint = new Point(8, 5);

            Route route = sa.findRoute(startPoint, targetPoint);

            assertAll(() -> {
                assertThat(route.getPath()).hasSize(0);
                assertThat(route.getTargetPoint()).isEqualTo(new Point(8, 5));
            });
        }

        @Test
        @DisplayName("상로 이동 경로를 생성할 수 있다.")
        void checkUpRouteMovable() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(8, 4);
            Point targetPoint = new Point(7, 4);

            Route route = sa.findRoute(startPoint, targetPoint);

            assertAll(() -> {
                assertThat(route.getPath()).hasSize(0);
                assertThat(route.getTargetPoint()).isEqualTo(new Point(7, 4));
            });
        }

        @Test
        @DisplayName("하로 이동 경로를 생성할 수 있다.")
        void checkDownRouteMovable() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(8, 4);
            Point targetPoint = new Point(9, 4);

            Route route = sa.findRoute(startPoint, targetPoint);

            assertAll(() -> {
                assertThat(route.getPath()).hasSize(0);
                assertThat(route.getTargetPoint()).isEqualTo(new Point(9, 4));
            });
        }

        @Test
        @DisplayName("상-우측대각선으로 이동 경로를 생성할 수 있다.")
        void checkUpRightRouteMovable() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(8, 4);
            Point targetPoint = new Point(7, 5);

            Route route = sa.findRoute(startPoint, targetPoint);

            assertAll(() -> {
                assertThat(route.getPath()).hasSize(0);
                assertThat(route.getTargetPoint()).isEqualTo(new Point(7, 5));
            });
        }

        @Test
        @DisplayName("상-좌측대각선으로 이동 경로를 생성할 수 있다.")
        void checkUpLeftRouteMovable() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(8, 4);
            Point targetPoint = new Point(7, 3);

            Route route = sa.findRoute(startPoint, targetPoint);

            assertAll(() -> {
                assertThat(route.getPath()).hasSize(0);
                assertThat(route.getTargetPoint()).isEqualTo(new Point(7, 3));
            });
        }

        @Test
        @DisplayName("하-우측대각선으로 이동 경로를 생성할 수 있다.")
        void checkDownRightRouteMovable() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(8, 4);
            Point targetPoint = new Point(9, 5);

            Route route = sa.findRoute(startPoint, targetPoint);

            assertAll(() -> {
                assertThat(route.getPath()).hasSize(0);
                assertThat(route.getTargetPoint()).isEqualTo(new Point(9, 5));
            });
        }

        @Test
        @DisplayName("하-좌측대각선으로 이동 경로를 생성할 수 있다.")
        void checkDownLeftRouteMovable() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(8, 4);
            Point targetPoint = new Point(9, 3);

            Route route = sa.findRoute(startPoint, targetPoint);

            assertAll(() -> {
                assertThat(route.getPath()).hasSize(0);
                assertThat(route.getTargetPoint()).isEqualTo(new Point(9, 3));
            });
        }
    }
}
