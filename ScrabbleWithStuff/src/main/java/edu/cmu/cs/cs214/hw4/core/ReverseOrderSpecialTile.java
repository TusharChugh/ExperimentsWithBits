package edu.cmu.cs.cs214.hw4.core;

import java.util.Map;

/**
 * Reverses the order of play when activated
 * Is Activated after the score is calculated
 */
public class ReverseOrderSpecialTile implements SpecialTile {
    private final SpecialTileType tileType = SpecialTileType.ReverseOrder;

    /**
     * Creates an instance of ReverseOrderSpecialTile
     */
    public ReverseOrderSpecialTile() {
        //Do nothing
    }

    /**
     * No action in pre-activation
     *
     * @param game     the instance of the game
     * @param location location where the special tile is activated
     */
    @Override
    public void preScoringActivate(Game game, Location location) {
        //Do nothing
    }

    /**
     * Is activated after the score has been calculated and reverses the turn phase
     *
     * @param game     instance of game
     * @param location location where the special tile is activated
     */
    @Override
    public void postScoringActivate(Game game, Location location) {
        game.reverseTurnPhase();
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
