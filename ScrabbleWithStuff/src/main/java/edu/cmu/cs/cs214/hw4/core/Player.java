package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents player of a scrabble game
 */
public class Player {
    //Name of the player
    private final String playerName;
    //Score
    private int playerScore = 0;
    //Player ID
    private final int playerId;
    //Rack containing LetterTiles
    private List<LetterTile> rack = new ArrayList<>();
    //Rack containing Special Tiles
    private List<SpecialTile> specialTilesRack = new ArrayList<>();

    /**
     * Constructor to create an instance of a player
     * @param playerName Name of the players (String)
     * @param playerId id of the player (should be unique for different players)
     */
    public Player(String playerName, int playerId) {
        this.playerName = Objects.requireNonNull(playerName, "Player Name can't be null");
        this.playerId = playerId;
    }

    /**
     * Returns name of the player
     * @return name of the player
     */
    public String name () {
        return new String(playerName);
    }

    /**
     * Returns score of the player
     * @return score of the player
     */
    public int score() {
        return playerScore;
    }

    /**
     * Retuns the id of the player
     * @return
     */
    public int id() {return playerId; }

    /**
     * Modify the score of the player. Can add or substact
     * @param value score to modify
     * @param add add (true) or subtract (false)
     * @return updated score
     */
    int addOrSubtractScore(int value, boolean add) {
        if(add)
            playerScore += value;
        else
            playerScore -= value;
        return playerScore;
    }

    /**
     * Get the rack of the player
     * @return rack of player(List<LetterTiles>)
     */
    public List<LetterTile> getRack() {
        return new ArrayList<>(rack);
    }

    /**
     * Assign a new rack of letter tiles to the player
     * @param rack the rack (List<LetterTiles>)
     */
    void setRack(List<LetterTile> rack) {
        this.rack = new ArrayList<>(rack);
    }

    /**
     * Get rack with special tiles
     * @return rack with special tiles (list)
     */
    public List<SpecialTile> getSpecialRack() {
        return new ArrayList<>(specialTilesRack);
    }

    /**
     * Add a new special Tile on the rack
     * @param specialTile a SpecialTile
     */
    void addSpecialTile(SpecialTile specialTile) {
        specialTilesRack.add(specialTile);
    }

    /**
     * Remove a special tile from the special tile rack
     * @param specialTile  a SpecialTile
     */
    void removeSpecialTile(SpecialTile specialTile) {
        int index = 0;
        for(SpecialTile tile: specialTilesRack) {
            if(tile.equals(specialTile)) {
                specialTilesRack.remove(index);
                break;
            }
            index++;
        }
    }

    /**
     * Checks if two instances of players are equal
     * @param o instance to compare
     * @return true if equal else false
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Player))
            return false;
        Player player = (Player) o;
        return playerId == player.playerId;
    }

    /**
     * Return the hashcode for the player
     * @return the hascode
     */
    @Override
    public  int hashCode() {
        return  31 * Objects.hashCode(playerId);
    }

    /**
     * String representation of the player i.e. the playerName
     * @return playerName in string format
     */
    @Override
    public String toString() {
        return playerName;
    }
}
