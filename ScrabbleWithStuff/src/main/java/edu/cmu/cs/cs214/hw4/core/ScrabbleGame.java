package edu.cmu.cs.cs214.hw4.core;

import java.util.List;
import java.util.Map;

/**
 * Created by Tushar on 23-Oct-16.
 */
public interface ScrabbleGame {
    int BOARD_SIZE = 15;
    int RACK_SIZE = 7;

    /**
     * get all the player playing the game
     * @return list of players
     */
    List<Player> getPlayers();

    /**
     * Get the player having the turn
     * @return player
     */
    Player currentPlayer();

    /**
     * challenge the move
     * @param player player who challenges the move
     */
    void challenge(Player player);

    /**
     * Exchange the tiles from the rack
     * @param letterTilesMap letter tiles and location on the rack
     * @return true if exchanged
     */
    boolean exchangeTiles(Map<Integer, LetterTile> letterTilesMap);

    /**
     * Pass the turn (move)
     */
    void pass();

    /**
     * Place the letter tiles on the board
     * @param letterTilesMap letter tiles with corresponding locations
     */
    void placeLetterTiles(Map<Location, LetterTile> letterTilesMap);

    /**
     * Place special tile on the board
     * @param specialTile special tile
     * @param location location
     */
    void placeSpecialTile(SpecialTile specialTile, Location location);

    /**
     * Purchase the special tile
     * @param specialTileType special tile type
     * @return special tile
     */
    SpecialTile purchaseSpecialTile(SpecialTileType specialTileType);

    /**
     * Get the scrabble board
     * @return board
     */
    Board getBoard();

    /**
     * start the game
     */
    void startGame();

    /**
     * Register a game change listener to be notified of game change events.
     *
     * @param listener The listener to be notified of game change events.
     */
    void addGameChangeListener(GameChangeListener listener);
}
