package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Removes letter and special tile from the board with the manhattan distance of 3 squares
 * deletes the squares
 * Last move score is reverted back and a new score is calculated with the left over tiles
 */
public class BoomSpecialTile implements SpecialTile{
    private final SpecialTileType tileType = SpecialTileType.Boom;
    private final boolean add = true;
    private final boolean sub = false;
    private final int maxDistance = 3;
    private Map<Location, LetterTile> lastMoveTiles = new HashMap<>();

    /**
     * Constructor to create the instance of this tile
     */
    public BoomSpecialTile() {
        //Do nothing
    }

    /**
     * Doesn't get activated
     * @param game the instance of the game
     * @param location location where the special tile is activated
     */
    @Override
    public void preScoringActivate(Game game, Location location) {
    }

    /**
     * Executes the boom action and revert back the score and calculates new score
     * @param game instance of game
     * @param location location where the special tile is activated
     */
    @Override
    public void postScoringActivate(Game game, Location location) {
        lastMoveTiles = game.getLastMoveTiles();
        boomActivate(game, location);
        game.getBoard().squareAt(location).removeAllSpecialTiles();
        game.currentPlayer().addOrSubtractScore(game.getLastMoveScore(), sub);
        int adjustedScore = getAdjustedScore(game);
        game.currentPlayer().addOrSubtractScore(adjustedScore, add);
        game.setLastMoveScore(adjustedScore);

    }

    /**
     * Calculate adjusted score
     * Tile can be broken into zero, one or two halfs
     * @param game instance of the game
     * @return score
     */
    private int getAdjustedScore(Game game) {
        int firstHalfScore = 0;
        int secondHalfScore = 0;
        List<Location> locations = new ArrayList<>(lastMoveTiles.keySet());

        //Get first half
        Map<Location, LetterTile> firstHalf = new HashMap<>();
        for(Location location: locations) {
            if(lastMoveTiles.containsKey(location)) {
                firstHalf.put(location, lastMoveTiles.get(location));
            }
        }
        if(!firstHalf.isEmpty())
            firstHalfScore = game.calculateTotalScore(firstHalf);

        List<Location> locs = new ArrayList<>(firstHalf.keySet());
        for(Location loc: locs) {
            if(lastMoveTiles.containsKey(loc))
                lastMoveTiles.remove(loc);
        }

        if(!lastMoveTiles.isEmpty()) {
            List<Location> secLocs = new ArrayList<>(lastMoveTiles.keySet());

            //Get Second half
            Map<Location, LetterTile> secondHalf = new HashMap<>();
            for(Location location: secLocs) {
                if(lastMoveTiles.containsKey(location)) {
                    firstHalf.put(location, lastMoveTiles.get(location));
                }
            }
            if(!secLocs.isEmpty())
                secondHalfScore = game.calculateTotalScore(firstHalf);

        }
        return firstHalfScore + secondHalfScore;
    }

    /**
     * The boom action to delete tiles with manhattan distance of 3 units
     * @param game instance of the game
     * @param location location of activation
     */
    private  void boomActivate(Game game, Location location) {
        int x = location.row();
        int y = location.col();
        Map<Location, LetterTile> removedTiles = new HashMap<>();
        for(int i = -maxDistance; i <= maxDistance; i++){
            for(int j = -maxDistance; j <= maxDistance; j++) {
                Location loc = new Location(x+i,y+j);
                if(game.getBoard().isLocationInBoard(loc)) {
                    int manhattanDistance = Math.abs(i) + Math.abs(j);
                    if(manhattanDistance <= maxDistance) {
                        LetterTile removedTile = game.getBoard().removeLetterTile(loc);
                        removedTiles.put(loc, removedTile);
                        lastMoveTiles.remove(loc);
                        Map<Player, SpecialTile> removedSpecialTiles = game.getBoard().removeAllSpecialTiles(loc);
                        for(Player player: removedSpecialTiles.keySet()) {
                            game.notifySpecialTilesRemoved(loc, player);
                        }
                    }
                }
            }
        }
        game.notifyLetterTilesRemoved(removedTiles);
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
