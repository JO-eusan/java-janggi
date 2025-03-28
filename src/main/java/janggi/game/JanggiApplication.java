package janggi.game;

import janggi.dao.BoardDao;
import janggi.dao.RunningPiecesDao;
import janggi.view.BoardView;
import janggi.view.InputView;

public class JanggiApplication {

    public static void main(String[] args) {
        JanggiGame game = new JanggiGame(
            new InputView(),
            new BoardView(),
            new BoardDao(),
            new RunningPiecesDao()
        );
        game.start();
    }
}
