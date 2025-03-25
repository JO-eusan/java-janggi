package janggi.point;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.game.Team;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PalacePointsTest {

    @Nested
    @DisplayName("궁성 범위 테스트")
    class PalaceRangeTest {

        @ParameterizedTest
        @MethodSource("getCardinalPointForHan")
        @DisplayName("한나라의 상하좌우로 이동할 수 있는 궁성 영역을 확인할 수 있다.")
        void checkInPalaceCardinalRangeForHan(Point point) {
            assertAll(() -> {
                assertThat(PalacePoints.isInPalaceWithMovableCardinal(Team.HAN, point)).isTrue();
                assertThat(PalacePoints.isInPalaceWithMovableCardinal(Team.CHO, point)).isFalse();
            });
        }

        private static Stream<Arguments> getCardinalPointForHan() {
            return Stream.of(
                Arguments.arguments(new Point(0, 4)),
                Arguments.arguments(new Point(1, 3)),
                Arguments.arguments(new Point(1, 5)),
                Arguments.arguments(new Point(2, 4))
            );
        }

        @ParameterizedTest
        @MethodSource("getDiagonalPointForHan")
        @DisplayName("한나라의 대각선으로도 이동할 수 있는 궁성 영역을 확인할 수 있다.")
        void checkOutPalaceDiagonalRangeForHan(Point point) {
            assertAll(() -> {
                assertThat(PalacePoints.isInPalaceWithMovableDiagonal(Team.HAN, point)).isTrue();
                assertThat(PalacePoints.isInPalaceWithMovableDiagonal(Team.CHO, point)).isFalse();
            });
        }

        private static Stream<Arguments> getDiagonalPointForHan() {
            return Stream.of(
                Arguments.arguments(new Point(0, 3)),
                Arguments.arguments(new Point(0, 5)),
                Arguments.arguments(new Point(1, 4)),
                Arguments.arguments(new Point(2, 3)),
                Arguments.arguments(new Point(2, 5))
            );
        }

        @ParameterizedTest
        @MethodSource("getCardinalPointForCho")
        @DisplayName("초나라의 상하좌우로 이동할 수 있는 궁성 영역을 확인할 수 있다.")
        void checkInPalaceCardinalRangeForCho(Point point) {
            assertAll(() -> {
                assertThat(PalacePoints.isInPalaceWithMovableCardinal(Team.CHO, point)).isTrue();
                assertThat(PalacePoints.isInPalaceWithMovableCardinal(Team.HAN, point)).isFalse();
            });
        }

        private static Stream<Arguments> getCardinalPointForCho() {
            return Stream.of(
                Arguments.arguments(new Point(7, 4)),
                Arguments.arguments(new Point(8, 3)),
                Arguments.arguments(new Point(8, 5)),
                Arguments.arguments(new Point(9, 4))
            );
        }

        @ParameterizedTest
        @MethodSource("getDiagonalPointForCho")
        @DisplayName("한나라의 대각선으로도 이동할 수 있는 궁성 영역을 확인할 수 있다.")
        void checkOutPalaceDiagonalRangeForCho(Point point) {
            assertAll(() -> {
                assertThat(PalacePoints.isInPalaceWithMovableDiagonal(Team.CHO, point)).isTrue();
                assertThat(PalacePoints.isInPalaceWithMovableDiagonal(Team.HAN, point)).isFalse();
            });
        }

        private static Stream<Arguments> getDiagonalPointForCho() {
            return Stream.of(
                Arguments.arguments(new Point(7, 3)),
                Arguments.arguments(new Point(7, 5)),
                Arguments.arguments(new Point(8, 4)),
                Arguments.arguments(new Point(9, 3)),
                Arguments.arguments(new Point(9, 5))
            );
        }

        @Test
        @DisplayName("궁성 내부에 존재하면 true를 반환한다.")
        void checkOutOfPalaceRange() {
            Point point = new Point(8, 4);

            assertThat(PalacePoints.isInPalaceRange(Team.CHO, point)).isTrue();
        }

        @Test
        @DisplayName("궁성 외부에 존재하면 false를 반환한다.")
        void checkInPalaceRange() {
            Point point = new Point(6, 6);

            assertThat(PalacePoints.isInPalaceRange(Team.CHO, point)).isFalse();
        }
    }
}
