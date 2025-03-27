package janggi.game.team;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.piece.Gung;
import janggi.piece.Movable;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TeamScoreTest {

    @Nested
    @DisplayName("승리팀 결정 테스트")
    class WinnerTest {

        @Test
        @DisplayName("한나라의 궁이 남았다면 한나라가 승리한다.")
        void winHanWithGung() {
            Map<Team, Double> scores = Map.of(
                Team.CHO, 13.0,
                Team.HAN, 12.5
            );
            List<Movable> decisionPiece = List.of(new Gung(Team.HAN));

            TeamScore teamScore = new TeamScore(scores);

            assertThat(teamScore.judgeWinner(decisionPiece)).isEqualTo(Team.HAN);
        }

        @Test
        @DisplayName("초나라의 궁이 남았다면 초나라가 승리한다.")
        void winChoWithGung() {
            Map<Team, Double> scores = Map.of(
                Team.CHO, 11.0,
                Team.HAN, 12.5
            );
            List<Movable> decisionPiece = List.of(new Gung(Team.CHO));

            TeamScore teamScore = new TeamScore(scores);

            assertThat(teamScore.judgeWinner(decisionPiece)).isEqualTo(Team.CHO);
        }

        @Test
        @DisplayName("궁을 잡기 전이라면 점수가 높은 초나라가 승리한다.")
        void winChoWithScore() {
            Map<Team, Double> scores = Map.of(
                Team.CHO, 13.0,
                Team.HAN, 7.0
            );
            List<Movable> decisionPiece = List.of(new Gung(Team.CHO), new Gung(Team.HAN));

            TeamScore teamScore = new TeamScore(scores);

            assertThat(teamScore.judgeWinner(decisionPiece)).isEqualTo(Team.CHO);
        }

        @Test
        @DisplayName("궁을 잡기 전이라면 점수가 높은 한나라가 승리한다.")
        void winHanWithScore() {
            Map<Team, Double> scores = Map.of(
                Team.CHO, 7.5,
                Team.HAN, 13.0
            );
            List<Movable> decisionPiece = List.of(new Gung(Team.CHO), new Gung(Team.HAN));

            TeamScore teamScore = new TeamScore(scores);

            assertThat(teamScore.judgeWinner(decisionPiece)).isEqualTo(Team.HAN);
        }
    }
}
