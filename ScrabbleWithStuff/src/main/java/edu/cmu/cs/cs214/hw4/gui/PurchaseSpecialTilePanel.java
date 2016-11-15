package edu.cmu.cs.cs214.hw4.gui;

import edu.cmu.cs.cs214.hw4.core.ScrabbleGame;
import edu.cmu.cs.cs214.hw4.core.SpecialTile;
import edu.cmu.cs.cs214.hw4.core.SpecialTileType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

/**
 * Panel containing elements for purchasing a new tile
 */
public class PurchaseSpecialTilePanel extends JPanel {
    private List<JRadioButton> specialTileButtons;
    private final ScrabbleGame game;
    private JPanel specialTilesPanel;
    private final String buttonText = "Purchase Special Tiles";
    private ButtonGroup group = new ButtonGroup();

    private final JButton purchaseTiles = new JButton();

    /**
     * Constructor
     * @param game instance of the game
     */
    public PurchaseSpecialTilePanel(ScrabbleGame game) {
        this.game = game;
        addSpecialTiles();
        addPurchaseButton();
        specialTilesPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    /**
     * Add all special tiles to a radio buttons
     * Only one of them can be selected at t time
     */
    private void addSpecialTiles() {
        specialTilesPanel = new JPanel();
        specialTilesPanel.setLayout(new GridLayout(2, 3));
        specialTileButtons = new ArrayList<>();
        for(SpecialTileType specialTileType: SpecialTileType.values()) {
            specialTileButtons.add(new JRadioButton(specialTileType.toString() + ": Cost - " + specialTileType.price()));
        }

        for(JRadioButton button: specialTileButtons) {
            specialTilesPanel.add(button);
            group.add(button);
        }
    }

    /**
     * Adds the purchase button
     */
    private void addPurchaseButton() {
        purchaseTiles.setPreferredSize(new Dimension(50,30));
        purchaseTiles.setText(buttonText);
        purchaseTiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<Integer, SpecialTile> tileToExchange = new HashMap<>();
                int i =0;
                for(SpecialTileType specialTileType: SpecialTileType.values()) {
                    if(specialTileButtons.get(i).isSelected()) {
                        try {game.purchaseSpecialTile(specialTileType);}
                        catch (Exception ex) {
                            JOptionPane.showMessageDialog(new JFrame(), "Not enough money", "Message",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    i++;
                }
            }
        });

    }

    /**
     * Retuns the panel with containing required elements necessary for purchasing the tiles
     * @return
     */
    public JPanel getSpecialTilePanel() {
        JPanel specialPanel = new JPanel();
        specialPanel.setLayout(new GridLayout(0, 1));
        specialPanel.add(specialTilesPanel);
        specialPanel.add(purchaseTiles);
        specialPanel.setBackground(Color.PINK);
        return specialPanel;
    }

    /**
     * Deactivate the panel. Only the button is deactivated
     */
    public void deactivatePanel() {
        purchaseTiles.setEnabled(false);
    }

    /**
     * Activate the panel. Only the button is deactivated
     */
    public void activatePanel() {
        purchaseTiles.setEnabled(true);
    }
}
