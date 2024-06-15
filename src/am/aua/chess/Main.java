package am.aua.chess;

import am.aua.chess.cli.ChessConsole;
import am.aua.chess.ui.ChessUI;

/**
 * The am.aua.chess.Main class contains the main method to start the chess game.
 */
public class Main {
    /**
     * The main method to start the chess game.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            ChessUI ui = new ChessUI();
        } else if (args[0].equals("-console")){
            ChessConsole chessConsole = new ChessConsole();
            chessConsole.run();
        }
    }
}
