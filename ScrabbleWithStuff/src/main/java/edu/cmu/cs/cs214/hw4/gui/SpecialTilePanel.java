package edu.cmu.cs.cs214.hw4.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.cmu.cs.cs214.hw4.core.Location;
import edu.cmu.cs.cs214.hw4.core.ScrabbleGame;
import edu.cmu.cs.cs214.hw4.core.SpecialTile;
import edu.cmu.cs.cs214.hw4.core.SpecialTileType;

/**
 * Creates a panel which enables users to play the special tiles
 */
public class SpecialTilePanel extends JPanel {
    private final JComboBox comboBox;
    private final JTextField locationText;
    private final JButton placeButton;
    private final ScrabbleGame game;
    private final String buttonText = "Place Spl. Tile";
    JPanel panel = new JPanel();

    /**
     * Constructor
     * @param game instance of the game
     */
    public SpecialTilePanel(ScrabbleGame game) {
        comboBox = new JComboBox(SpecialTileType.values());
        placeButton = new JButton(buttonText);
        addActionListenerButton();
        locationText = new JTextField();
        locationText.setPreferredSize(new Dimension(40,30));
        this.game = game;
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    /**
     * adds the action lister
     */
    private void addActionListenerButton() {
        placeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SpecialTileType specialTileType = (SpecialTileType) comboBox.getSelectedItem();
                boolean tilePlacingHandled = false;
                for(SpecialTile specialTile: game.currentPlayer().getSpecialRack()){
                    if(specialTile.specialTileType().equals(specialTileType)) {
                        if (locationText.getText().contains(",")) {
                            String result[] = locationText.getText().split(",");
                            try {
                                game.placeSpecialTile(
                                        specialTile, new Location(Integer.parseInt(result[0]), Integer.parseInt(result[1])));
                                tilePlacingHandled = true;
                            }
                            catch (Exception ex) {
                                JOptionPane.showMessageDialog(new JFrame(), ex.toString(), "Message",
                                        JOptionPane.ERROR_MESSAGE);
                                tilePlacingHandled = true;
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(new JFrame(), "Invalid Location", "Message",
                                    JOptionPane.ERROR_MESSAGE);
                            tilePlacingHandled = true;
                        }
                    }
                }
                if(!tilePlacingHandled)
                    JOptionPane.showMessageDialog(new JFrame(), "Tile not purchased", "Message",
                            JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public JPanel getSpecialTilePanel() {
        panel.setLayout(new BorderLayout());
        panel.add(comboBox, BorderLayout.LINE_START);
        panel.add(locationText, BorderLayout.CENTER);
        panel.add(placeButton, BorderLayout.SOUTH);
        return panel;
    }

    public void deactivatePanel() {
        placeButton.setEnabled(false);
    }
    public void activatePanel() {
        placeButton.setEnabled(true);
    }
}
