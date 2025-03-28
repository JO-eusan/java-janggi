package janggi.dao;

import janggi.game.Board;
import janggi.game.team.Team;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BoardDao {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "password";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결 오류:" + e);
        }
    }

    public void saveBoard(Board board, int startMinute) {
        String query = "INSERT INTO board VALUES(?, ?, ?)";
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, board.getBoardName());
            preparedStatement.setString(2, board.getTurn().getText());
            preparedStatement.setInt(3, startMinute);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Team findTurnByBoardName(String boardName) {
        String query = "SELECT * FROM board WHERE board_name=?";
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, boardName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Team.of(resultSet.getString("turn"));
            }
            throw new IllegalArgumentException("존재하지 않은 board_name입니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findStartTimeByBoardName(String boardName) {
        String query = "SELECT * FROM board WHERE board_name=?";
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, boardName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("start_minute");
            }
            throw new IllegalArgumentException("존재하지 않은 board_name입니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existByBoardName(String boardName) {
        String query = "SELECT * FROM board WHERE board_name=?";
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, boardName);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTurn(Board board) {
        String query = "UPDATE board "
            + "SET turn=? "
            + "WHERE board_name=?";
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, board.getTurn().getText());
            preparedStatement.setString(2, board.getBoardName());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAllBoards() {
        String query = "DELETE FROM board";
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
