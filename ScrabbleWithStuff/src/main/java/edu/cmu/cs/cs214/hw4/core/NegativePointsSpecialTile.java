package edu.cmu.cs.cs214.hw4.core;

import java.util.Map;

/**
 * This special tiles penalizes the user who played the tile with equivalent of negative point
 * of the words formed in this turn
 */
public class NegativePointsSpecialTile implements SpecialTile {
    private final SpecialTileType tileType = SpecialTileType.NegativePoints;
    private final boolean sub = false;
    private final int multiplier = 2;

    /**
     * Constructor to create the instance of this tile
     */
    public NegativePointsSpecialTile() {
        //Do nothing
    }

    /**
     * Not activated with negative points tile
     * @param game the instance of the game
     * @param location location where the special tile is activated
     */
    @Override
    public void preScoringActivate(Game game, Location location) {
        //Do nothing
    }

    /**
     * Activated after the scoring has been done in the game
     * This subtracts the score two times as the game adds the score once
     * @param game instance of game
     * @param location location where the special tile is activated
     */
    @Override
    public void postScoringActivate(Game game, Location location) {
        game.currentPlayer().addOrSubtractScore(multiplier * game.getLastMoveScore(), sub);
        game.setLastMoveScore(game.getLastMoveScore() - multiplier * game.getLastMoveScore());
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
