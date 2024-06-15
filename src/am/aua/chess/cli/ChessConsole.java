package am.aua.chess.cli;

import am.aua.chess.core.Chess;
import am.aua.chess.core.Move;
import am.aua.chess.core.Position;
import am.aua.chess.puzzles.Puzzle;
import am.aua.chess.puzzles.PuzzleDatabase;

import java.util.Scanner;

/**
 * The <code>am.aua.chess.cli.ChessConsole</code> class provides a console user interface for playing chess.
 * It allows players to make moves and displays the game board in the console.
 */
public class ChessConsole {
    // an instance variable to keep track of the ongoing game
    private Chess game;

    // an instance variable to represent the puzzle database
    private PuzzleDatabase database;

    /**
     * Constructs a ChessConsole object and initializes the puzzle database.
     */
    public ChessConsole() {
        this.database = new PuzzleDatabase();
    }

    /**
     * Initiates the chess game and starts the console interface.
     */
    public void play() {
        Scanner sc = new Scanner(System.in);
        String inputLine;
        print();

        while (!game.isGameOver()) {
            if (game.getTurn() == Chess.PieceColor.WHITE) {
                System.out.println("White’s move : ");
            } else {
                System.out.println("Black’s move : ");
            }
            inputLine = sc.nextLine();
            String[] input = inputLine.split(" ");

            Position p1 = null, p2 = null;

            if (input.length >= 1) {
                if (input[0].equals("resign")) {
                    System.out.println(game.getTurn() + " has resigned.");
                    return;
                }

                if (input[0].equals("debug")) {
                    debug();
                    print();
                    continue;
                }
                p1 = Position.generateFromString(input[0]);

                if (p1 == null || game.getPieceAt(p1) == null) {
                    System.out.println("Invalid position. Please try again.");
                    continue;
                }

                if (input.length == 1) {
                    if (game.getPieceAt(p1).getPieceColor() != game.getTurn())
                        System.out.println("Invalid Selection: that piece belongs to the opponent.");
                    print(p1);
                } else if (input.length == 2) {
                    if (game.getPieceAt(p1).getPieceColor() != game.getTurn()) {
                        System.out.println("Invalid Selection: that piece belongs to the opponent.");
                        continue;
                    }

                    boolean success = false;

                    p2 = Position.generateFromString(input[1]);
                    if (p2 != null) {
                        Move m = new Move(p1, p2);
                        success = game.performMove(m);
                    }
                    if (!success) System.out.println("Invalid Move: please try again.");

                    print();
                }
            }
        }
    }

    /**
     * This method runs the chess console application, allowing users to interact with the game.
     * It starts by displaying a welcome message and giving instructions on how to use the console.
     * It will continue to read user input until the user types "q" to quit the program.
     * Users can enter commands to play chess, list puzzles, add puzzles from a file, or play a specific puzzle.
     * After each command, the instructions are shown again for user guidance.
     * When exiting the program, the database is saved to preserve any modifications.
     */
    public void run() {
        Scanner sc = new Scanner(System.in);
        String inputLine;

        System.out.println("Welcome to Chess Console!");
        printInstructions();
        inputLine = sc.nextLine();
        while(!inputLine.equals("q")) {
            try {
                if (inputLine.equals("p")) {
                    game = new Chess();
                    play();
                }
                else if (inputLine.equals("l")) {
                    int databaseSize = database.getSize();
                    for ( int i = 0; i < databaseSize; i++)
                        System.out.println(i + ": " + database.getPuzzle(i));
                }
                else if (inputLine.startsWith("a "))
                    database.addPuzzlesFromFile(inputLine.substring(2));
                else if (inputLine.startsWith("p ")) {
                    int puzzleNumber = Integer.parseInt(inputLine.substring(2));
                    Puzzle puzzle = database.getPuzzle(puzzleNumber);
                    game = new Chess (puzzle.getArrangement(), puzzle.getTurn());
                    play();
                }
                else
                    System.out.println("Unknown instruction. Please try again.");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            printInstructions();
            inputLine = sc.nextLine();
        }
        database.save();
    }

    /**
     * Prints the instructions for using the chess console application.
     * Printed messages help to navigate easily while using the application
     */
    private void printInstructions () {
        System.out.println("Input 'p' to play chess.");
        System.out.println("Input 'l' to list the puzzles in the database.");
        System.out.println("Input 'a <filename>' to add new puzzles into the" + " database.");
        System.out.println("Input 'p <number>' to play a puzzle.");
        System.out.println("If you want to end the program, input 'q'.");
    }

    /**
     * Prints the current state of the chessboard with highlights.
     *
     * @param origin The position from which to calculate reachable positions for highlights. Can be null if no highlights are needed.
     */
    public void print(Position origin) {
        Position[] highlights = null;
        if (origin != null)
            highlights = game.reachableFrom(origin);

        for (int i = 0; i < Chess.BOARD_RANKS; i++) {
            System.out.print((Chess.BOARD_RANKS - i) + " ");

            for (int j = 0; j < Chess.BOARD_FILES; j++) {
                boolean isHighlighted = false;

                //include the square the piece is currently at
                if (origin != null
                        && origin.getRank() == i && origin.getFile() == j)
                    isHighlighted = true;

                for (int k = 0; highlights != null && k < highlights.length; k++)
                    if (highlights[k].getRank() == i
                            && highlights[k].getFile() == j) {
                        isHighlighted = true;
                        break;
                    }

                if (isHighlighted)
                    System.out.print("\u001b[31m");

                System.out.print("[");

                Position current = Position.generateFromRankAndFile(i, j);
                if (game.isEmpty(current))
                    System.out.print(" ");
                else
                    System.out.print(game.getPieceAt(current));

                System.out.print("]");

                if (isHighlighted)
                    System.out.print("\u001b[0m");
            }
            System.out.println();
        }
        System.out.println("   A  B  C  D  E  F  G  H ");
        System.out.println();
    }

    /**
     * Prints the current state of the chess board without highlighting any pieces.
     */
    public void print() {
        print(null);
    }

    /**
     * A method to debug the code.
     */
    public void debug() {
        System.out.println("This method is for testing purposes.");
        //System.out.println(game.getAllDestinationsByColor(Chess.PieceColor.WHITE).length);
    }
}
