package edu.cmu.cs.cs214.hw4.gui;

import edu.cmu.cs.cs214.hw4.core.LetterTile;
import edu.cmu.cs.cs214.hw4.core.ScrabbleGame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.BorderFactory;


/**
 * Panel for the tile exchange dialog box
 */
public class ExchangeTilesDialog extends JPanel{
    //Checkboxes to select letter tile to be exchanged
    private List<JCheckBox> lettersBox;
    //Letter tiles on the rack of the player
    private  List<LetterTile> letterTiles;
    //Exchanged letter tile
    private List<LetterTile> result;
    //Instance of the game
    private final ScrabbleGame game;
    //Button to fire the exchange process
    private final JButton selectTile = new JButton();

    /**
     * Constructor to instantiate the panel
     * @param game instance of the game
     * @param letterTiles letter tiles on the rack of the player
     */
    public ExchangeTilesDialog(ScrabbleGame game, List<LetterTile> letterTiles) {
        super(new BorderLayout());
        this.game = game;

        this.letterTiles = letterTiles;

        //Add checkboxes
        lettersBox = new ArrayList<>(letterTiles.size());
        int i = 0;
        for(LetterTile letterTile: letterTiles) {
            lettersBox.add(new JCheckBox(letterTile.name()));
        }

        //Add button to exchange the selected tiles
        selectTile.setPreferredSize(new Dimension(300,70));
        selectTile.setText("Exchange");
        //Action listener on mouse click exchanges the selected tile
        selectTile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<Integer, LetterTile> tileToExchange = new HashMap<>();
                int i =0;
                for(LetterTile letterTile: letterTiles) {
                    if(lettersBox.get(i).isSelected()) {
                        tileToExchange.put(i, letterTile);
                    }
                    i++;
                }
                ExchangeTilesDialog.this.game.exchangeTiles(tileToExchange);
            }
        });

        //Put the check boxes in a column in a panel
        JPanel checkPanel = new JPanel(new GridLayout(0, 1));
        for(JCheckBox letter: lettersBox) {
            checkPanel.add(letter);
        }

        add(checkPanel, BorderLayout.LINE_START);
        add(selectTile, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }
}
