package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a 2D location in discrete environment (row, col)
 */
public class Location {
    private final int rowVal;
    private final int colVal;
    private final int next = 1;

    /**
     * Construtor
     * @param rowVal row
     * @param colVal column
     */
    public Location(int rowVal, int colVal) {
        this.rowVal = rowVal;
        this.colVal = colVal;
    }

    /**
     * Copy constructor
     * @param location location
     */
    public Location(Location location) {
        rowVal = location.rowVal;
        colVal = location.colVal;
    }

    /**
     * returns row value
     * @return row value
     */
    public int row() {
        return rowVal;
    }

    /**
     * returns column value
     * @return column value
     */
    public int col() {
        return colVal;
    }

    /**
     * Returns the up of this location
     * @return up of this location
     */
    public Location up() {
        return new Location(rowVal - next, colVal);
    }

    /**
     * Returns the down of this location
     * @return down of this location
     */
    public Location down() {
        return new Location(rowVal + next, colVal);
    }

    /**
     * Returns the right of this location
     * @return up right this location
     */
    public Location right() {
        return new Location(rowVal, colVal + next);
    }

    /**
     * Returns the left of this location
     * @return left of this location
     */
    public Location left() {
        return new Location(rowVal, colVal - next);
    }

    /**
     * Returns the up, down, left and right neigbuours
     * @return neighbours of this location
     */
    public List<Location> neighbours() {
        List<Location> locations = new ArrayList<>();
        locations.add(left());
        locations.add(right());
        locations.add(up());
        locations.add(down());
        return locations;
    }

    /**
     * Compares two instance of Location
     * @param o Object of location
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Location))
            return false;
        Location loc = (Location) o;
        return loc.row() == rowVal && loc.col() == colVal;
    }

    /**
     * Returns the hascode
     * @return hascode
     */
    @Override
    public  int hashCode() {
        int result = 17;
        result = 31 * rowVal + colVal;
        return  result;
    }

    /**
     * String representation of format row,col
     * @return string
     */
    @Override
    public String toString() {
        return Integer.toString(rowVal) + ", " + Integer.toString(colVal);
    }
}
