package janggi.point;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.game.Team;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PalacePointsTest {

    @Nested
    @DisplayName("궁성 범위 테스트")
    class PalaceRangeTest {

        @ParameterizedTest
        @MethodSource("getPalacePointForHan")
        @DisplayName("한나라의 궁성을 확인할 수 있다.")
        void checkInPalaceRange(Point point) {
            assertAll(() -> {
                assertThat(PalacePoints.isInPalaceRange(Team.HAN, point)).isTrue();
                assertThat(PalacePoints.isInPalaceRange(Team.CHO, point)).isFalse();
            });
        }

        @ParameterizedTest
        @MethodSource("getPalacePointForCho")
        @DisplayName("초나라의 궁성을 확인할 수 있다.")
        void checkOutPalaceRange(Point point) {
            assertAll(() -> {
                assertThat(PalacePoints.isInPalaceRange(Team.CHO, point)).isTrue();
                assertThat(PalacePoints.isInPalaceRange(Team.HAN, point)).isFalse();
            });
        }

        private static Stream<Arguments> getPalacePointForHan() {
            return Stream.of(
                Arguments.arguments(new Point(0, 3)),
                Arguments.arguments(new Point(0, 4)),
                Arguments.arguments(new Point(0, 5)),
                Arguments.arguments(new Point(1, 3)),
                Arguments.arguments(new Point(1, 4)),
                Arguments.arguments(new Point(1, 5)),
                Arguments.arguments(new Point(2, 3)),
                Arguments.arguments(new Point(2, 4)),
                Arguments.arguments(new Point(2, 5))
            );
        }

        private static Stream<Arguments> getPalacePointForCho() {
            return Stream.of(
                Arguments.arguments(new Point(7, 3)),
                Arguments.arguments(new Point(7, 4)),
                Arguments.arguments(new Point(7, 5)),
                Arguments.arguments(new Point(8, 3)),
                Arguments.arguments(new Point(8, 4)),
                Arguments.arguments(new Point(8, 5)),
                Arguments.arguments(new Point(9, 3)),
                Arguments.arguments(new Point(9, 4)),
                Arguments.arguments(new Point(9, 5))
            );
        }
    }
}
