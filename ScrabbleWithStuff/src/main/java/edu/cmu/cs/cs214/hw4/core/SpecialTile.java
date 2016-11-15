package edu.cmu.cs.cs214.hw4.core;

/**
 * Interface to represent the types
 */
public interface SpecialTile {
    /**
     * Activate before the score is calculated and can perform the action
     * @param game the instance of the game
     * @param location location where the special tile is activated
     */
    void preScoringActivate(Game game, Location location);

    /**
     * Activate after the score is calculated
     * @param game instance of game
     * @param location location where the special tile is activated
     */
    void postScoringActivate(Game game, Location location);

    /**
     * Returns the type of special tile
     * @return SpecialTileType type of special tile
     */
    SpecialTileType specialTileType();
}
