package am.aua.chess.puzzles;

import am.aua.chess.exceptions.MalformedPuzzleException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class PuzzleDatabase {
    // The relative path to the database file
    private static final String DATABASE_RELATIVE_PATH = "src/am/aua/chess/puzzles/database.txt";

    // An array of puzzles without any duplicates
    private ArrayList<Puzzle> puzzles;

    /**
     * A no-arg constructor that loads the puzzles from the database file into the puzzle
     * array;
     */
    public PuzzleDatabase() {
        load();
    }

    /**
     * A method that loads the puzzles from the database file into the puzzle array.
     * If any problem occurs, it shows the corresponding message and aborts the program.
     */
    private void load() {
        try {
            Scanner inputStream = new Scanner(new FileInputStream(DATABASE_RELATIVE_PATH));
            int puzzlesCount = inputStream.nextInt();
            puzzles = new ArrayList<>(puzzlesCount);
            inputStream.nextLine();
            for (int i = 0; i < puzzlesCount; i++) {
                Puzzle current = new Puzzle(inputStream.nextLine(), inputStream.nextLine());
                if (!contains(current))
                    puzzles.add(current);
            }
            Collections.sort(puzzles);
            inputStream.close();
        } catch (MalformedPuzzleException e) {
            System.err.println("Malformed puzzle in the database.");
            System.exit(0);
        } catch (FileNotFoundException e){
            System.err.println("Cannot open the database file.");
            System.exit(0);
        }
    }

    /**
     * A method that saves the puzzles from the array into the database file.
     * If any problem occurs, it shows the corresponding message and aborts the program.
     */
    public void save() {
        try {
            PrintWriter pw = new PrintWriter(DATABASE_RELATIVE_PATH);
            pw.println(puzzles.size());
            for (int i = 0; i < puzzles.size(); i++)
                pw.println(puzzles.get(i));
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot save into the database file.");
            System.exit(0);
        }
    }

    /**
     * Adds puzzles from a file to the puzzle database, if not already present in the database.
     * If any problem occurs during loading, the program aborts.
     *
     * @param filename The name of the file containing puzzle textual descriptions
     */
    public void addPuzzlesFromFile(String filename) {
        try {
            Scanner sc = new Scanner(new FileInputStream(filename));
            while (sc.hasNextLine()) {
                Puzzle current = new Puzzle(sc.nextLine(), sc.nextLine());
                if (!contains(current))
                    puzzles.add(current);
            }
            Collections.sort(puzzles);
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the specified file with puzzles.");
            System.exit(0);
        }
        catch (MalformedPuzzleException e) {
            System.out.println("Malformed puzzle in the database.");
            System.exit(0);
        }
    }

    /**
     * Accessor method to return the number of puzzles in the database.
     *
     * @return The number of puzzles in the database
     */
    public int getSize() {
        return puzzles.size();
    }

    /**
     * Accessor method to return the puzzle from the database at the specified index.
     *
     * @param index The index of the puzzle in the database array
     * @return The puzzle at the specified index
     */
    public Puzzle getPuzzle(int index) {
        if (index >= 0 && index < getSize()) {
            return puzzles.get(index);
        }
        return null;
    }

    /**
     * Checks if the puzzle database contains a specific puzzle.
     *
     * @param p the puzzle to be checked
     * @return true if the puzzle is found in the database, false otherwise
     */
    private boolean contains(Puzzle p) {
        for (int i = 0; i < puzzles.size(); i++)
            if (puzzles.get(i).equals(p))
                return true;
        return false;
    }
}
