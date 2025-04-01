package janggi.game;

import janggi.dao.BoardDao;
import janggi.dao.RunningPiecesDao;
import janggi.dao.connector.MySQLConnector;
import janggi.view.BoardView;
import janggi.view.InputView;

public class JanggiApplication {

    public static void main(String[] args) {
        BoardDao boardDao = new BoardDao(MySQLConnector.createConnection());
        RunningPiecesDao runningPiecesDao = new RunningPiecesDao(MySQLConnector.createConnection());

        JanggiGame game = new JanggiGame(
            new InputView(),
            new BoardView(),
            boardDao, runningPiecesDao
        );
        game.start();

        boardDao.closeConnector();
        runningPiecesDao.closeConnector();
    }
}
