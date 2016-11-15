package edu.cmu.cs.cs214.hw4.core;

import java.util.Map;

/**
 *  When this tile is triggered, each tile on the board shifts down one position and the bottom row
 *  of the board becomes the new top row of the board.
 */
public class RotateDownSpecialTile implements  SpecialTile{
    private final SpecialTileType tileType = SpecialTileType.RotateDown;
    private final int firstRow = 1;
    /**
     * Constructor to create the instance of this tile
     */
    public RotateDownSpecialTile() {
        //Do nothing
    }

    /**
     * This tile is not activated before score calculation
     * @param game the instance of the game
     * @param location location where the special tile is activated
     */
    @Override
    public void preScoringActivate(Game game, Location location) {
        //Do nothing
    }

    /**
     * Activated after the score is calculated
     * Shift down all the tiles
     * @param game instance of game
     * @param location location where the special tile is activated
     */
    @Override
    public void postScoringActivate(Game game, Location location) {
        Board newBoard = new Board();
        Board oldBoard = game.getBoard();
        for(int col = 1; col <= oldBoard.dimension(); col++){
            for(int row = 1; row <= oldBoard.dimension(); row++){
                Location loc = new Location(row, col);
                LetterTile letterTile = oldBoard.squareAt(loc).getLetterTile();
                if(letterTile != null) {
                    if(oldBoard.isLocationInBoard(loc.down())) {
                        newBoard.placeLetterTile(letterTile, loc.down());
                    }
                    else
                        newBoard.placeLetterTile(letterTile, new Location(firstRow, loc.col()));
                }
            }
        }
        Map<Player, SpecialTile> removedSpecialTiles = game.getBoard().removeAllSpecialTiles(location);
        for(Player player: removedSpecialTiles.keySet()) {
            game.notifySpecialTilesRemoved(location, player);
        }
        game.changeBoard(newBoard);
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
