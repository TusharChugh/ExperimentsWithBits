package edu.cmu.cs.cs214.hw4.gui;

import java.io.IOException;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import edu.cmu.cs.cs214.hw4.core.DictionaryHelper;
import edu.cmu.cs.cs214.hw4.core.Game;

/**
 * Main class to start the game
 * Demo code of GUI components from the oracle website
 */
public class ScrabbleGUI {
    //Instance of the game
    private static Game game;
    //Set of words in the dictionary
    private static Set<String> words;
    //Path to the dictionary
    private static String dictionaryPath;
    //31 is to get the same behavior everytime. Change it to play
    private static final Random random = new Random(31);

    /**
     * PSVM :
     * @param args : no args required
     */
    public static void main(String[] args) {
        dictionaryPath = args[0];
        //Read file. Throw exception is not able to read the file
        try{
            words =  DictionaryHelper.listOfWords(dictionaryPath);
        }
        catch (IOException e) {
            System.out.println(e.toString());
        }
        SwingUtilities.invokeLater(() -> {
            addPlayersAndStartGame();
        });
    }

    /**
     * Add the players and start the game
     */
    private static void addPlayersAndStartGame() {
        JFrame frame = new JFrame("Add new players");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //add chat client, participants will be added by SimpleChatClient
        frame.add(new AddPlayerPanel(frame, words, random));

        //display the JFrame
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
