package edu.cmu.cs.cs214.hw4.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a square which constitutes to create a board
 */
public class  Square {
    private final SquareType squareType;
    private final Location location;
    private LetterTile letterTile = null;
    private Map<Player, SpecialTile> specialTilesMap = new LinkedHashMap<>();

    /**
     * Constructor
     * @param squareType type of the square
     * @param location Location of the square
     */
    Square(SquareType squareType, Location location) {
        this.location = Objects.requireNonNull(location, "Location cannot be null");
        this.squareType = squareType;
    }

    /**
     * Copy constructor
     * @param square instance of another square
     */
    Square(Square square){
        squareType = square.squareType;
        location = square.location;
        letterTile = square.letterTile;
        specialTilesMap = square.specialTilesMap;
    }

    /**
     * returns location
     * @return the location of the square
     */
    Location location() {
        return location;
    }

    /**
     * Takes the letter tile and places on the up of square
     * Can only place one tile
     * @param letterTile letter Tile
     */
    void placeLetterTile(LetterTile letterTile) {
        this.letterTile = Objects.requireNonNull(letterTile, "Null letter tile can't be placed");
    }

    /**
     * Gets the letter tile place on the up of square
     * @return letter tile
     */
    LetterTile getLetterTile() {
        return letterTile;
    }

    /**
     * Removes the letter tile from the up of square
     * @return removed letter tile
     */
    LetterTile removeLetterTile() {
        LetterTile letterTileCopy = letterTile;
        letterTile = null;
        return letterTileCopy;
    }

    /**
     * Places special tile on the up of the square
     * A player can only place one tile
     * @param specialTile special tile
     * @param player player
     */
    void placeSpecialTile(SpecialTile specialTile, Player player) {
        if(letterTile != null)
            throw new IllegalArgumentException("Special tile can't be placed on the up of  letter tile");
        Objects.requireNonNull(specialTile, "Null special tile can't be placed");
        Objects.requireNonNull(player, "Player can't be null");
        if(specialTilesMap.get(player) != null)
            throw new IllegalArgumentException("Special Tile already with this player exists");
        specialTilesMap.put(player, specialTile);
    }

    /**
     * Removes all the special tile from the up
     * @return map of removed tiles
     */
    Map<Player, SpecialTile> removeAllSpecialTiles() {
        Map<Player, SpecialTile> specialTileMapCopy = new LinkedHashMap<>(specialTilesMap);
        specialTilesMap.clear();
        return specialTileMapCopy;
    }

    /**
     * all the special tile from the up
     * @return all the special tile from the up
     */
    Map<Player, SpecialTile> getSpecialTiles() {
        return new LinkedHashMap<>(specialTilesMap);
    }

    /**
     * Type of the Square
     * @return squaretype
     */
    public SquareType squareType() {
        return squareType;
    }

    /**
     * Returns string representation of the square
     * If there is not letter tile places then returns square else letter tile
     * @return string
     */
    @Override
    public String toString() {
        if(letterTile != null)
            return letterTile.toString();
        else
            return squareType.toString();
    }
}
