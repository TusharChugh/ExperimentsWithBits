package edu.cmu.cs.cs214.hw4.core;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Objects;
import java.util.Collections;

/**
 * Represents the bag which contains 98 tiles
 */
class Bag {
    //98 total tiles in the game
    private final int numTiles = 98;
    //Rack size (one rack per player)
    private final int rackSize = 7;
    //List of letter tiles presently in the bag
    private final List<LetterTile> letters = new ArrayList<>(numTiles);
    //Random generator for shuffle
    private final Random random;

    /**
     * Constructor : Starte with 98 tiles
     * @param random instance of Random used as a seed for shuffle
     */
    Bag(Random random) {
        this.random = Objects.requireNonNull(random, "Random object can't be null");
        fillBagWithTiles();
        Collections.shuffle(letters, random);
    }

    /**
     * Fills the bag with 98 letter tiles at the start of the game
     */
    private void fillBagWithTiles() {
        int letterIndex = 0;
        for(LetterTile letterTile: LetterTile.values()) {
            for(int i = 0; i < letterTile.multiplicity(); i++, letterIndex++)
                letters.add(letterIndex, letterTile);
        }
    }

    /**
     * Checks if the bag is empty
     * @return true if bag is empty, false otherwise
     */
    boolean isEmpty() {
        return letters.size() == 0;
    }

    /**
     * For players to replisnish the tiles on the rack
     * @param rack the rack of the player (rack is list of letter tiles)
     */
    void replenishTiles(List<LetterTile> rack) {
        int tilesToReplenish = rackSize - rack.size();
        if(tilesToReplenish > letters.size())
            tilesToReplenish = letters.size();
        rack.addAll(letters.subList(letters.size() - tilesToReplenish, letters.size()));
        letters.subList(letters.size() - tilesToReplenish, letters.size()).clear();
    }

    /**
     * For players to exchange one or more tile
     * Returns empty list if there are less than 7 tiles in the bag
     * @param tiles tiles to exchange
     * @return new list of exchanged tiles
     */
    List<LetterTile> exchangeTiles(List<LetterTile> tiles) {
        List<LetterTile> result = new ArrayList<>();
        if(!(letters.size() < rackSize)) {
            letters.addAll(tiles);
            Collections.shuffle(letters, random);
            result.addAll(letters.subList(letters.size() - tiles.size(), letters.size()));
            letters.subList(letters.size() - tiles.size(), letters.size()).clear();
        }
        return result;
    }
}
