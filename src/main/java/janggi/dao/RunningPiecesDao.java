package janggi.dao;

import janggi.dao.connector.DBConnector;
import janggi.game.team.Team;
import janggi.piece.Byeong;
import janggi.piece.Cha;
import janggi.piece.Gung;
import janggi.piece.Ma;
import janggi.piece.Movable;
import janggi.piece.Po;
import janggi.piece.Sa;
import janggi.piece.Sang;
import janggi.piece.pieces.RunningPieces;
import janggi.piece.type.PieceType;
import janggi.point.Point;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class RunningPiecesDao {

    private final DBConnector connector;

    public RunningPiecesDao(DBConnector connector) {
        this.connector = connector;
    }

    public void saveRunningPieces(String boardName, RunningPieces runningPieces) {
        for (Entry<Point, Movable> entry : runningPieces.getRunningPieces().entrySet()) {
            Movable piece = entry.getValue();
            Point point = entry.getKey();

            savePiece(boardName, piece, point);
        }
    }

    private void savePiece(String boardName, Movable piece, Point point) {
        String query = "INSERT INTO pieces "
            + "(board_name, type, team, point_row, point_column) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connector.handleQuery(query)) {
            preparedStatement.setString(1, boardName);
            preparedStatement.setString(2, piece.getName());
            preparedStatement.setString(3, piece.getTeam().getText());
            preparedStatement.setInt(4, point.row());
            preparedStatement.setInt(5, point.column());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public RunningPieces findByBoardName(String boardName) {
        String query = "SELECT * FROM pieces WHERE board_name=?";
        try (PreparedStatement preparedStatement = connector.handleQuery(query)) {
            preparedStatement.setString(1, boardName);

            Map<Point, Movable> pieces = new HashMap<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int row = resultSet.getInt("point_row");
                int column = resultSet.getInt("point_column");
                Point point = new Point(row, column);

                String type = resultSet.getString("type");
                String team = resultSet.getString("team");
                Movable piece = createPiece(type, team);

                pieces.put(point, piece);
            }
            return new RunningPieces(pieces);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Movable createPiece(String type, String team) {
        Map<String, Movable> pieceCreators = Map.of(
            PieceType.GUNG.getText(), new Gung(Team.of(team)),
            PieceType.SA.getText(), new Sa(Team.of(team)),
            PieceType.CHA.getText(), new Cha(Team.of(team)),
            PieceType.PO.getText(), new Po(Team.of(team)),
            PieceType.MA.getText(), new Ma(Team.of(team)),
            PieceType.SANG.getText(), new Sang(Team.of(team)),
            PieceType.BYEONG.getText(), new Byeong(Team.of(team))
        );
        return pieceCreators.get(type);
    }

    public void updatePoint(String boardName, RunningPieces runningPieces,
        Point startPoint, Point targetPoint) {
        String query = "UPDATE pieces "
            + "SET point_row=?, point_column=? "
            + "WHERE board_name=? && type=? && team=? && point_row=? && point_column=?";

        Movable piece = runningPieces.findPieceByPoint(targetPoint);
        try (PreparedStatement preparedStatement = connector.handleQuery(query)) {
            preparedStatement.setInt(1, targetPoint.row());
            preparedStatement.setInt(2, targetPoint.column());
            preparedStatement.setString(3, boardName);
            preparedStatement.setString(4, piece.getName());
            preparedStatement.setString(5, piece.getTeam().getText());
            preparedStatement.setInt(6, startPoint.row());
            preparedStatement.setInt(7, startPoint.column());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePiece(String boardName, Point targetPoint) {
        if (existByPoint(targetPoint)) {
            String query = "DELETE FROM pieces "
                + "WHERE board_name=? && point_row=? && point_column=?";
            try (PreparedStatement preparedStatement = connector.handleQuery(query)) {
                preparedStatement.setString(1, boardName);
                preparedStatement.setInt(2, targetPoint.row());
                preparedStatement.setInt(3, targetPoint.column());
                preparedStatement.executeUpdate();
            } catch (final SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean existByPoint(Point point) {
        String query = "SELECT * FROM pieces "
            + "WHERE point_row=? && point_column=?";
        try (PreparedStatement preparedStatement = connector.handleQuery(query)) {
            preparedStatement.setInt(1, point.row());
            preparedStatement.setInt(2, point.column());

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAllPieces() {
        String query = "DELETE FROM pieces";
        try (PreparedStatement preparedStatement = connector.handleQuery(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnector() {
        connector.close();
    }
}
