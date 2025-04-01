package janggi.dao;

import janggi.dao.connector.DBConnector;
import janggi.game.Board;
import janggi.game.team.Team;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public class BoardDao {

    private final DBConnector connector;

    public BoardDao(DBConnector connector) {
        this.connector = connector;
    }

    public void saveBoard(Board board, LocalTime startTime) {
        String query = "INSERT INTO board (board_name, turn, start_time) VALUES(?, ?, ?)";
        try (PreparedStatement preparedStatement = connector.handleQuery(query)) {
            preparedStatement.setString(1, board.getBoardName());
            preparedStatement.setString(2, board.getTurn().getText());
            preparedStatement.setString(3, startTime.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Team findTurnByBoardName(String boardName) {
        String query = "SELECT turn FROM board WHERE board_name=?";
        try (PreparedStatement preparedStatement = connector.handleQuery(query)) {
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

    public LocalTime findStartTimeByBoardName(String boardName) {
        String query = "SELECT start_time FROM board WHERE board_name=?";
        try (PreparedStatement preparedStatement = connector.handleQuery(query)) {
            preparedStatement.setString(1, boardName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return LocalTime.parse(resultSet.getString("start_time"));
            }
            throw new IllegalArgumentException("존재하지 않은 board_name입니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existByBoardName(String boardName) {
        String query = "SELECT EXISTS (SELECT board_name FROM board WHERE board_name=?)";
        try (PreparedStatement preparedStatement = connector.handleQuery(query)) {
            preparedStatement.setString(1, boardName);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getBoolean(1);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTurn(Board board) {
        String query = "UPDATE board "
            + "SET turn=? "
            + "WHERE board_name=?";
        try (PreparedStatement preparedStatement = connector.handleQuery(query)) {
            preparedStatement.setString(1, board.getTurn().getText());
            preparedStatement.setString(2, board.getBoardName());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByBoardName(String boardName) {
        String query = "DELETE FROM board WHERE board_name=?";
        try (PreparedStatement preparedStatement = connector.handleQuery(query)) {
            preparedStatement.setString(1, boardName);

            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnector() {
        connector.close();
    }
}
