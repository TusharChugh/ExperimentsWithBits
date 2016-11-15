package edu.cmu.cs.cs214.hw4.core;

import java.util.Map;

/**
 * Adds 10 extra points to the player who activates this tile
 */
class TenExtraPointsSpecialTile implements SpecialTile {
    private final SpecialTileType tileType = SpecialTileType.TenExtraPoints;
    private final int scoreToAdd = 10;
    private final boolean add = true;

    /**
     * Constructor to create the instance of this tile
     */
    public TenExtraPointsSpecialTile() {
        //Do nothing
    }

    /**
     * Activated before the scoring. Adds the score
     * @param game the instance of the game
     * @param location location where the special tile is activated
     */
    @Override
    public void preScoringActivate(Game game, Location location) {
        game.currentPlayer().addOrSubtractScore(scoreToAdd, add);
        game.setLastMoveScore(game.getLastMoveScore() + scoreToAdd);
    }

    /**
     * Does nothing on activation
     * @param game instance of game
     * @param location location where the special tile is activated
     */
    @Override
    public void postScoringActivate(Game game, Location location) {
        Map<Player, SpecialTile> removedSpecialTiles = game.getBoard().removeAllSpecialTiles(location);
        for(Player player: removedSpecialTiles.keySet()) {
            game.notifySpecialTilesRemoved(location, player);
        }
    }

    /**
     * Returns special tile type
     *
     * @return SpecialTileType
     */
    @Override
    public SpecialTileType specialTileType() { return tileType; }

    /**
     * String representation of the tile type
     *
     * @return String special tiletype
     */
    public String toString() {
        return tileType.toString();
    }
}
