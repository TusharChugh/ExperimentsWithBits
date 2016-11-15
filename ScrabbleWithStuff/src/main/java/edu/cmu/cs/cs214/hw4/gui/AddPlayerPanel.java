package edu.cmu.cs.cs214.hw4.gui;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.ScrabbleGame;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;


/**
 * Panel which helps in adding player to the game
 */
public class AddPlayerPanel extends JPanel {
    /** The JFrame from which this chat is established. */
    private JFrame parentFrame;
    /** The names of the participants in this chat. */
    private final List<String> names;
    //Instance of the random
    private final Random random;
    //Set of words in dicitonary
    private final Set<String> dictionary;
    //Position offset for chat Windows
    private static final int CHAT_WINDOW_POS_OFFSET = 30;

    /**
     * Constuction
     * @param frame frame to render components
     * @param words set of words of dictionary
     * @param random instance of random
     */
    public AddPlayerPanel(JFrame frame, Set<String> words, Random random) {
        this.random = random;
        this.dictionary = words;

        this.parentFrame = frame;
        this.names = new ArrayList<>();

        // Create the components to add to the panel.
        JLabel participantLabel = new JLabel("Name: ");

        // Must be final to be accessible to the anonymous class.
        final JTextField participantText = new JTextField(20);

        JButton participantButton = new JButton("Add player");
        JPanel participantPanel = new JPanel();
        participantPanel.setLayout(new BorderLayout());
        participantPanel.add(participantLabel, BorderLayout.WEST);
        participantPanel.add(participantText, BorderLayout.CENTER);
        participantPanel.add(participantButton, BorderLayout.EAST);

        // Defines an action listener to handle the addition of new participants
        ActionListener newParticipantListener = e -> {
            String name = participantText.getText().trim();
            if (!name.isEmpty() && !names.contains(name)) {
                names.add(name);
            }
            participantText.setText("");
            participantText.requestFocus();
        };

        // notify the action listener when participant Button is pressed
        participantButton.addActionListener(newParticipantListener);

        // notify the action listener when "Enter" key is hit
        participantText.addActionListener(newParticipantListener);

        JButton createButton = new JButton("Start New Game");
        createButton.addActionListener(e -> {
            // Starts a new chat when the createButton is clicked.
            if (!names.isEmpty()) {
                startNewGame();
            }
        });

        // Adds the components we've created to the panel (and to the window).
        setLayout(new BorderLayout());
        add(participantPanel, BorderLayout.NORTH);
        add(createButton, BorderLayout.SOUTH);
        setVisible(true);
    }

    /**
     * Start the game after adding the players
     */
    private void startNewGame() {
        if(names.size() < 2 || names.size() > 4) {
            String message = "Add 2-4 players";
            JOptionPane.showMessageDialog(new JFrame(), message, "Message",
                    JOptionPane.ERROR_MESSAGE);
        }
        else {
            parentFrame.dispose();
            parentFrame = null;
            // Creates a new window for each participant.
            int pos = 0; // Distance of the next chat window from origin in pixels
            ScrabbleGame game = new Game(names, dictionary, random);
            for (Player player : game.getPlayers()) {
                JFrame frame = new JFrame("Scrabble Game -- " + player);
                GamePanel gamePanel = new GamePanel(game, player, frame.getContentPane());
                frame.add(gamePanel);
                frame.addWindowListener(new WindowAdapter() {
                    // Called to unsubscribe chat client from chat when its window
                    // is closed
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                    }
                });

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocation(pos, pos);
                pos += CHAT_WINDOW_POS_OFFSET;

                frame.setResizable(true);
                frame.setVisible(true);
            }
        }
    }
}
