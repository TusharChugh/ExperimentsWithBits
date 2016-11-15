package edu.cmu.cs.cs214.hw4.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.cmu.cs.cs214.hw4.core.GameChangeListener;
import edu.cmu.cs.cs214.hw4.core.LetterTile;
import edu.cmu.cs.cs214.hw4.core.Location;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.ScrabbleGame;
import edu.cmu.cs.cs214.hw4.core.SpecialTile;
import edu.cmu.cs.cs214.hw4.core.SpecialTileType;
import edu.cmu.cs.cs214.hw4.core.Square;
import edu.cmu.cs.cs214.hw4.core.SquareType;

/**
 * Represents the total game panel
 */
public class GamePanel extends JPanel implements GameChangeListener {
    //Instance of the game
    private final ScrabbleGame game;
    //Array of 2D buttons for square
    private final JButton[][] squares;
    //Button representing rack of the players
    private final JButton[] tiles;
    //Text fields to enter the locations for placement of the tiles
    private final JTextField[] textFields;
    //Size of the grid
    private final int gridSize;
    //Owner of the window/panel
    private final Player player;
    //Play, pass and exchange buttons
    private final JButton[] playMoveButtons;
    //Jlabels for Names of the players
    private final JLabel[] playerName;
    //Jlabers for score of the players
    private final JLabel[] playerScore;
    //Challenge button
    private JButton challengeButton;
    //Gui constants
    private static final boolean shouldFill = true;
    //GUI constants
    private static final boolean shouldWeightX = true;
    //GUI constants
    private static final boolean rightToLeft = false;
    //Special tile panel
    private SpecialTilePanel specialTilePanel;
    //Purchase panel
    private PurchaseSpecialTilePanel purchaseSpecialTilePanel;
    //Message box label
    private JLabel messageBox = new JLabel();
    //Color map for square types
    private Map<SquareType, Color> colorMap = new HashMap<>();
    /**
     * Move types
     */
    enum MoveType {PLAY, PASS, EXCHANGE}

    /**
     * Constructor to instantiate the game panel
     * @param game instance of the game
     * @param player owner of this panel
     * @param pane Container of the parent
     */
    public GamePanel(ScrabbleGame game, Player player, Container pane) {
        this.game = game;
        this.game.addGameChangeListener(this);
        this.player = player;
        setColorMap();
        gridSize = this.game.BOARD_SIZE;
        squares = new JButton[gridSize][gridSize];
        tiles = new JButton[this.game.RACK_SIZE];
        textFields = new JTextField[this.game.RACK_SIZE];
        playMoveButtons = new JButton[3];
        playerName = new JLabel[game.getPlayers().size()];
        playerScore = new JLabel[game.getPlayers().size()];
        addComponentsToPane(pane);
        this.game.startGame();
    }

    /**
     * Add the components to the container
     * in gridbag layout fashion
     * @param pane window pane
     */
    private void addComponentsToPane(Container pane) {
        if (rightToLeft) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        JButton button;
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }

        if (shouldWeightX) {
            c.weightx = 0.5;
        }
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        pane.add(createBoardPanel(), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(createScorecard(), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 1;
        pane.add(createPlayerRackPanel(), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 1;
        pane.add(createChallengeButton(), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 2;
        pane.add(createPlayMoveButtons(), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 3;
        pane.add(messageBox, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 3;
        pane.add(createPurchaseTilePanel(), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 3;
        pane.add(createPlaySpecialTilePanel(), c);
    }

    /**
     * Returns the play special tile pnel
     * @return JPanel
     */
    private JPanel createPlaySpecialTilePanel() {
        specialTilePanel = new SpecialTilePanel(game);
        return specialTilePanel.getSpecialTilePanel();
    }

    /**
     * Returns the panel for purchasing the tiles
     * @return
     */
    private JPanel createPurchaseTilePanel(){
        purchaseSpecialTilePanel = new PurchaseSpecialTilePanel(game);
        return purchaseSpecialTilePanel.getSpecialTilePanel();
    }

    /**
     * Creates the challenge button
     * @return JButton
     */
    private JButton createChallengeButton() {
        challengeButton = new JButton("Challenge");
        challengeButton.setPreferredSize(new Dimension(80,50));
        challengeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    game.challenge(player);
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(new JFrame(), ex.toString(), "Message",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return challengeButton;
    }

    //
    private void setColorMap() {
        colorMap.put(SquareType.RegularSquare, Color.LIGHT_GRAY);
        colorMap.put(SquareType.DoubleLetterSquare, Color.CYAN);
        colorMap.put(SquareType.TripleLetterSquare, Color.BLUE);
        colorMap.put(SquareType.DoubleWordSquare, Color.PINK);
        colorMap.put(SquareType.TripleWordSquare, Color.RED);
    }

    /**
     * Creates 2D array of buttons for the board layout
     * @return
     */
    private JPanel createBoardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(gridSize, gridSize));

        for(int row = 0; row < gridSize; row++) {
            for(int col = 0; col < gridSize; col++) {
                squares[row][col] = new JButton();
                Square square = game.getBoard().squareAt(new Location(row + 1, col + 1));
                squares[row][col].setText(square.toString());
                squares[row][col].setBackground(colorMap.get(square.squareType()));
                squares[row][col].setPreferredSize(new Dimension(60,30));
                panel.add(squares[row][col]);
            }
        }
        return panel;
    }

    /**
     * Creates player rack panel
     * @return
     */
    private JPanel createPlayerRackPanel() {
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(2,1));
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(game.RACK_SIZE));
        List<LetterTile> letterTiles = player.getRack();
        int index = 0;
        for(LetterTile letterTile: letterTiles) {
            tiles[index] = new JButton();
            String display = "<html>" + letterTile.letter()+ "<sub>" + letterTile.points() + "</sub> </html>";
            tiles[index].setText(display);
            tiles[index].setBackground(Color.ORANGE);
            panel.add(tiles[index++]);
        }
        JPanel panel2 = new JPanel(new FlowLayout(game.RACK_SIZE));
        index = 0;
        for(LetterTile letterTile: letterTiles) {
            textFields[index] = new JTextField(4);
            panel2.add( textFields[index++]);
        }
        panel1.add(panel, BorderLayout.NORTH);
        panel1.add(panel2, BorderLayout.SOUTH);
        return panel1;
    }

    /**
     * creates the scorecard containing name and score of all the players
     * @return
     */
    private JPanel createScorecard() {
        JPanel panel = new JPanel();
        JLabel scoreHeader = new JLabel("Scorecard");
        panel.setLayout(new GridLayout(2, 1, 0,0));
        panel.add(scoreHeader, BorderLayout.NORTH);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(game.getPlayers().size(),1,0,0));
        for(Player p: game.getPlayers()){
            playerName[p.id()] = new JLabel();
            playerScore[p.id()] = new JLabel();
            playerName[p.id()].setPreferredSize(new Dimension(50,20));
            playerScore[p.id()].setPreferredSize(new Dimension(50,20));
            playerName[p.id()].setText(p.name());
            playerScore[p.id()].setText(Integer.toString(p.score()));
            panel1.add(playerName[p.id()]);
            panel1.add(playerScore[p.id()]);
        }
        panel.add(panel1);
        return panel;
    }

    /**
     * Create play, move and exchange buttons
     * @return
     */
    private JPanel createPlayMoveButtons() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        int index = 0;
        for(MoveType move: MoveType.values()) {
            playMoveButtons[index] = new JButton();
            playMoveButtons[index].setText(move.toString());
            playMoveButtons[index].addActionListener(new CustomActionListener(move));
            playMoveButtons[index].setEnabled(false);
            panel.add(playMoveButtons[index++]);
        }
        return  panel;
    }

    /**
     * Message received handler
     * @param message message
     */
    @Override
    public void messageReceived(String message) {
        messageBox.setText(message);
        messageBox.updateUI();
    }

    /**
     * letter tile placed handler. Displays the tile on gui
     * @param location location where the tile is place
     * @param letterTile the letter tile
     */
    @Override
    public void letterTilePlaced (Location location, LetterTile letterTile) {
        String display = "<html>" + letterTile.letter()+ "<sub>" + letterTile.points() + "</sub> </html>";
        JLabel tile = new JLabel(display);
        tile.setBackground(Color.ORANGE);
        tile.setOpaque(true);
        squares[location.row() - 1][location.col() - 1].add(tile);
        squares[location.row() - 1][location.col() - 1].updateUI();
    }

    /**
     * Letter tile removed handler. Removed tile on GUI
     * @param location location where the tile is place
     * @param letterTile the letter tile
     */
    @Override
    public void letterTileRemoved (Location location, LetterTile letterTile) {
        squares[location.row() - 1][location.col() - 1].removeAll();
        squares[location.row() - 1][location.col() - 1].updateUI();
    }

    /**
     * Special tile placed handler. Displays the tile on gui
     * @param location location where the tile is place
     * @param specialTile the special tile
     */
    @Override
    public void specialTilePlaced(Location location, SpecialTile specialTile) {
        if(game.currentPlayer().equals(player)) {
            JLabel tile = new JLabel(specialTile.toString());
            tile.setBackground(Color.YELLOW);
            tile.setOpaque(true);
            squares[location.row() - 1][location.col() - 1].add(tile);
            squares[location.row() - 1][location.col() - 1].updateUI();
        }
    }

    /**
     * Special tile removed handler. Removes the tile from GUI
     * @param location location where the tile is place
     * @param player owner of the special tile
     */
    @Override
    public void specialTileRemoved(Location location, Player player) {
        List<String> specialTileNames = new ArrayList<>();
        for(SpecialTileType specialTileType: SpecialTileType.values()) {
            specialTileNames.add(specialTileType.toString());
        }
        if(this.player.equals(player)){
           Component[] components = squares[location.row() - 1][location.col() - 1].getComponents();
            for(Component component: components) {
                if (specialTileNames.contains(((JLabel) component).getText()))
                    squares[location.row() - 1][location.col() - 1].remove(component);
            }
        }
    }

    /**
     * Game ended handler
     * @param player winner
     */
    @Override
    public void gameEnded(Player player) {
        String message = player.name() + " won. Score :" + player.score();
        JOptionPane.showMessageDialog(new JFrame(), message, "Message",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Handler the situation when a player is changed. Displays relevant information in the
     * given GUI
     * @param curPlayer
     */
    @Override
    public void currentPlayerChanged(Player curPlayer) {
        for(JButton button: playMoveButtons) {
            button.setEnabled(curPlayer.equals(player));
        }
        if(curPlayer.equals(player)) {
            specialTilePanel.activatePanel();
            purchaseSpecialTilePanel.activatePanel();
        }
        else {
            specialTilePanel.deactivatePanel();
            purchaseSpecialTilePanel.deactivatePanel();
        }
        int index = 0;
        for(JButton tile: tiles) {
            if(curPlayer.equals(player)) {
                LetterTile letterTile = player.getRack().get(index++);
                String display = "<html>" + letterTile.letter() + "<sub>" + letterTile.points() + "</sub> </html>";
                tile.setText(display);
                tile.updateUI();
            }
        }
        for(Player p: game.getPlayers()) {
            playerScore[p.id()].setText(Integer.toString(p.score()));
            playerScore[p.id()].updateUI();
            playerName[p.id()].setForeground(Color.BLACK);
            if(curPlayer.equals(p)) {
                playerName[p.id()].setForeground(Color.RED);
                playerName[p.id()].updateUI();
            }
        }
    }

    /**
     * Class to handle moves
     */
    class CustomActionListener implements ActionListener{
        MoveType move;
        Map<Location, LetterTile> letterTileMap;
        CustomActionListener(MoveType move) {
            this.move = move;
        }

        /**
         * Action event handler
         * @param e action
         */
        public void actionPerformed(ActionEvent e) {
            //statusLabel.setText("Ok Button Clicked.");
            switch (move){
                case PLAY:
                    playMove();
                    break;
                case PASS:
                    game.pass();
                    break;
                case EXCHANGE:
                    JFrame frame = new JFrame("Exchange Tile");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    //Create and set up the content pane.
                    JComponent newContentPane = new ExchangeTilesDialog(game, player.getRack());
                    newContentPane.setOpaque(true); //content panes must be opaque
                    frame.setContentPane(newContentPane);

                    //Display the window.
                    frame.pack();
                    frame.setVisible(true);
                    break;

            }

        }

        /**
         * Verifies if move is valid and then plays
         */
        void playMove() {
            try {
                if(getTile() != null)
                    game.placeLetterTiles(getTile());
            }
            catch (IllegalArgumentException e){
                String message = "Invalid Move";
                JOptionPane.showMessageDialog(new JFrame(), message, "Message",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        /**
         * Get the tile
         * @return location and letter tile
         */
        Map<Location, LetterTile> getTile() {
            Map<Location, LetterTile> letterTilesMap = new HashMap<>();
            int index = 0;
            for(JTextField textF: textFields) {
                if(textF.getText().contains(",")) {
                    String result[] = textF.getText().split(",");
                    Location loc = new Location(Integer.parseInt(result[0]), Integer.parseInt(result[1]));
                    if(letterTilesMap.containsKey(loc)) {
                        JOptionPane.showMessageDialog(new JFrame(), "Tiles can't be at same location", "Message",
                                JOptionPane.ERROR_MESSAGE);
                        return null;
                    }
                    else
                    letterTilesMap.put(loc, game.currentPlayer().getRack().get(index));
                }
                index++;
            }
            return  letterTilesMap;
        }
    }
}