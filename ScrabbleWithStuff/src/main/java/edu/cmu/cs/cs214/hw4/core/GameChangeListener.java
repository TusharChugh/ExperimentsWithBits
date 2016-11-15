package edu.cmu.cs.cs214.hw4.core;

/**
 * Game listener interface bridges the interaction between listeners
 * (such as GUI) and the implementation of scrabble game
 */
public interface GameChangeListener {
    /**
     * Notify the listener that letter tile has been placed
     * @param location location where the tile is place
     * @param letterTile the letter tile
     */
    void letterTilePlaced (Location location, LetterTile letterTile);

    /**
     * Notify the listener that letter tile has been removed
     * @param location location where the tile is place
     * @param letterTile the letter tile
     */
    void letterTileRemoved(Location location, LetterTile letterTile);

    /**
     * Notify the listener that special tile has been placed
     * @param location location where the tile is place
     * @param specialTile the special tile
     */
    void specialTilePlaced(Location location, SpecialTile specialTile);

    /**
     * Notify the listener that special tile has been removed
     * @param location location where the tile is place
     * @param player owner of the special tile
     */
    void specialTileRemoved(Location location, Player player);

    /**
     * Notify the listener if there is change in the player
     * @param player the player who has the turn
     */
    void currentPlayerChanged(Player player);

    /**
     * Notify the listener that the game has been ended.
     * Informs the winning player
     * @param player winner
     */
    void gameEnded(Player player);

    /**
     * Notify the listener that a message has been received
     * @param message message
     */
    void messageReceived(String message);
}
