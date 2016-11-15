package edu.cmu.cs.cs214.hw4.core;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.List;
import java.util.Collection;


/**
 * Represents the scrabble game board with 15x15 squares
 * Letter and special tiles can be placed and removed from the board
 * Board also gives helper functions to verify moves
 */
public class Board {
    //Dimension of the board
    private final int boardDimension = 15;
    //Added border for iterators protection on all 4 sides
    private final int border = 1;
    //Represents center of the board
    private final int centerBoard = 8;
    //17x17 (only 15x15 are used for the game)
    private Square[][] squares = new Square[border + boardDimension + border]
                                   [border + boardDimension + border];
    private boolean empty = true;
    //Horizontal(across) and vertical(down) directions
    private final boolean horizontal = true;
    private final boolean vertical = false;
    //Count of number of tiles on board
    private int numTiles = 0;
    private Map<Location, LetterTile> letterTileMap = new HashMap<>();

    /**
     * Constructor
     * Creates a new board constituting squares as in a scrabble game
     */
    public Board() {
        generateBoardWithSquares();
    }

    /**
     * Copy constructor
     * @param board instance of board
     */
    Board(Board board) {
        generateBoardWithSquares();
        for(int col = 1; col <= boardDimension; col++){
            for(int row = 1; row <= boardDimension; row++) {
                Location loc = new Location(row, col);
                LetterTile letterTile= board.squareAt(loc).getLetterTile();
                if(letterTile != null)
                    placeLetterTile(letterTile, loc);
                for(Map.Entry<Player, SpecialTile> entry : board.squareAt(loc).getSpecialTiles().entrySet()) {
                    squareAt(loc).removeAllSpecialTiles();
                    placeSpecialTile(entry.getValue(), loc, entry.getKey());
                }
            }
        }
    }

    /**
     * isEmpty check
     * @return true if there are no letter tiles on board, false otherwise
     */
    public boolean isEmpty(){
        return numTiles==0;
    }

    /**
     * Places the letter tile on square at a particular location
     * @param letterTile lettertile to be placed
     * @param location location of the square
     */
    void placeLetterTile(LetterTile letterTile, Location location) {
        Objects.requireNonNull(letterTile, "Letter Tile can't be null");
        Objects.requireNonNull(location, "Location can't be null");
        if(isValidLocation(location)) {
            squareAtLoc(location).placeLetterTile(letterTile);
            letterTileMap.put(location, letterTile);
        }
        else
            throw new IllegalArgumentException("Location not valid");
        numTiles++;
    }

    /**
     * Places all the letter tiles at the specified locations
     * @param letterTileMap map of locations as keys and letterTile as values
     */
    void placeAllLetterTiles(Map<Location, LetterTile> letterTileMap) {
        for (Map.Entry<Location, LetterTile> entry : letterTileMap.entrySet()) {
            placeLetterTile(entry.getValue(), entry.getKey());
        }
    }

    /**
     * Places the special tile on the up of a square
     * @param specialTile special tile
     * @param location location
     * @param player player
     */
    void placeSpecialTile(SpecialTile specialTile, Location location, Player player) {
        Objects.requireNonNull(specialTile, "Letter Tile can't be null");
        Objects.requireNonNull(location, "Location can't be null");
        if(isValidLocation(location))
            squareAtLoc(location).placeSpecialTile(specialTile, player);
        else
            throw new IllegalArgumentException("Location not valid");
    }

    /**
     * Removes all the special tiles from the given location
     * @param location location
     * @return owner and the removed special tile
     */
    Map<Player, SpecialTile> removeAllSpecialTiles(Location location) {
        if(isLocationInBoard(location))
            return squareAt(location).removeAllSpecialTiles();
        return null;
    }

    /**
     * Gets all special tile  from the specified location
     * @param location location
     * @return map of player and special tiles
     */
    public Map<Player, SpecialTile> getSpecialTile(Location location) {
        Objects.requireNonNull(location, "Location can't be null");
        if(isValidLocation(location))
            return squareAt(location).getSpecialTiles();
        else
            throw new IllegalArgumentException("Location not valid");
    }

    /**
     * Removes the letter tile form the specified location
     * @param location location
     * @return lettertile
     */
    LetterTile removeLetterTile(Location location) {
        Objects.requireNonNull(location, "Location can't be null");
        isLocationInBoard(location);
        numTiles--;
        LetterTile removedTile = squareAtLoc(location).removeLetterTile();
        letterTileMap.remove(location);
        return removedTile;
    }

    /**
     * Removes all the letter tile from the specified location
     * @param locations locations
     * @return removed letter tiles
     */
    List<LetterTile> removeAllLetterTiles(Collection<Location> locations) {
        List<LetterTile> letterTiles = new ArrayList<>();
        for(Location location: locations)
            letterTiles.add(removeLetterTile(location));
        return letterTiles;
    }

    /**
     * Returns letter tile at the specified location
     * @param location location
     * @return letter tile
     */
    public LetterTile letterTileAt(Location location) {
        if(isLocationInBoard(location))
            return squareAtLoc(location).getLetterTile();
        else
            return null;
    }

    /**
     * Checks if the location is valid with 2 criteria
     * 1. Location is in board
     * 2. There is no tile already on the location
     * @param location location
     * @return true if valid else false
     */
    boolean isValidLocation(Location location) {
        return isLocationInBoard(location) && letterTileAt(location) == null;
    }

    /**
     * Checks if all the locations valid, should satisfy:
     * 1. Location is in board
     * 2. There is no tile already on the location
     * @param locations List of locations
     * @return true if all are valid, false otherwise
     */
    boolean areValidLocations(List<Location> locations) {
        for(Location location: locations) {
            if(!isValidLocation(location))
                return false;
        }
        return true;
    }

    /**
     * Checks if the location is inside boundary of the board (in 15x15)
     * @param location location
     * @return true if inside else false
     */
    boolean isLocationInBoard(Location location) {
        if(location.row() < 1 || location.row() >= boardDimension + border ||
                location.col() < 1 || location.col() >= boardDimension + border)
            return false;
        return true;
    }

    /**
     * Helper method to generate an empty board with squares
     */
    private void generateBoardWithSquares() {
        fillLeftTopQuadrant();
        replicateLeftTopToOtherQuadrants();
        fillRegularSquares();
    }

    /**
     * Fill up left quadtrant with squares
     */
    private void fillLeftTopQuadrant() {
        Location[] dwLocations = {
                new Location(2,2), new Location(3,3), new Location(4,4), new Location(5,5), new Location(8,8)};
        addSquaresAtLocations(dwLocations, SquareType.DoubleWordSquare);
        Location[] twLocations = {
                new Location(1,1), new Location(8,1), new Location(1,8)};
        addSquaresAtLocations(twLocations, SquareType.TripleWordSquare);
        Location[] dlLocations = {
                new Location(4,1), new Location(1,4), new Location(3,7), new Location(7,3), new Location(8,4),
                new Location(4,8) , new Location(7,7)};
        addSquaresAtLocations(dlLocations, SquareType.DoubleLetterSquare);
        Location[] tlLocations = {
                new Location(6,2), new Location(2,6), new Location(6,6)};
        addSquaresAtLocations(tlLocations, SquareType.TripleLetterSquare);
    }

    /**
     * Helper method to add the squares at specified locations
     * @param locations array of location
     * @param squareType squaretype (same for all locations)
     */
    private void addSquaresAtLocations(Location[] locations, SquareType squareType) {
        for(Location loc: locations)
            squares[loc.row()][loc.col()] = new Square(squareType, loc);
    }

    /**
     * Helper method to replicate up left to other 3 quadrant
     */
    private void replicateLeftTopToOtherQuadrants() {
        for(Square[] row: squares) {
            for(Square square: row) {
                if(square != null) {
                    Location loc = new Location(square.location());
                    squares[loc.row()][boardDimension + border - loc.col() ] =
                            new Square(square.squareType(), new Location(loc.row(), boardDimension + border - loc.col()));
                    squares[boardDimension + border - loc.row()][loc.col()] = new Square(square.squareType(),
                            new Location(boardDimension + border - loc.row(), loc.col()));
                    squares[boardDimension + border - loc.row()][boardDimension + border - loc.col()] =
                            new Square(square.squareType(),
                                    new Location(boardDimension + border - loc.row(),boardDimension + border - loc.col()));
                }
            }
        }
    }

    /**
     * Fill rest of the board with regular squares
     */
    private void fillRegularSquares() {
        for(int row = 1; row <= boardDimension; row++) {
            for (int col = 1; col <= boardDimension; col++) {
                if(squares[row][col] == null)
                    squares[row][col] = new Square(SquareType.RegularSquare, new Location(row, col));
            }
        }
    }

    /**
     * Checks if the list of locations are collinear in row (horizontally)
     * @param locations list of locations
     * @return true if collinear, false otherwise
     */
    boolean areCollinearInRow(List<Location> locations) {
        int row = locations.get(0).row();
        for(Location location: locations) {
            if(location.row() != row)
                return false;
        }
        return true;
    }

    /**
     * Checks if the list of locations are collinear in colums (vertically)
     * @param locations list of locations
     * @return true if collinear, false otherwise
     */
    boolean areCollinearInColumn(List<Location> locations) {
        int col = locations.get(0).col();
        for(Location location: locations) {
            if(location.col() != col)
                return false;
        }
        return true;
    }

    /**
     * Get first and last tiles in the sequence
     * @param locations locations of all the tiles
     * @param isHorizontal true if horizontal, false otherwise
     * @return Pair of first and last locations
     */
    Pair<Location, Location> getFirstAndLastElements(List<Location> locations, boolean isHorizontal) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        int same;
        if (isHorizontal)
            same = locations.get(0).row();
        else
            same = locations.get(0).col();

        Location location = locations.get(0);

        while (squareAt(location) != null && squareAt(location).getLetterTile() != null || locations.contains(location)) {
            if(isHorizontal) {
                if (location.col() < min) min = location.col();
                location = location.left();
            }
            else {
                if (location.row() < min) min = location.row();
                location = location.up();
            }
        }

        location = locations.get(0);

        while (squareAt(location) != null && (squareAt(location).getLetterTile() != null || locations.contains(location))) {
            if(isHorizontal) {
                if (location.col() > max) max = location.col();
                location = location.right();
            }
                else {
                if (location.row() > max) max = location.row();
                location = location.down();
            }
        }

        if(isHorizontal)
            return new Pair<>(new Location(same,min), new Location(same, max));
        else
            return new Pair<>(new Location(min, same), new Location(max, same));
    }

    /**
     * Checks if locations are collinear and continous
     * @param locations list of locations
     * @return true if conditions are satisfied, false otherwise
     */
    boolean areCollinearAndContinuous(List<Location> locations) {
        if(areCollinearInRow(locations))
            return noEmptySquareInRange(locations, horizontal);
        else if(areCollinearInColumn(locations))
            return noEmptySquareInRange(locations, vertical);
        return false;
    }

    /**
     * Returns maximum and minimum elements from the list
     * min is nearest to left top and max is farthest to left top
     * @param locations list of locations
     * @param isHorizontal to search horizontally or vertically
     * @return
     */
    private Pair<Location, Location> getMaxAndMinElements(List<Location> locations, boolean isHorizontal) {
        Location min = locations.get(0);
        Location max = locations.get(0);
        for(Location location: locations) {
            if(isHorizontal) {
                if(location.col() < min.col())
                    min = location;
                if(location.col() > max.col())
                    max = location;
            }
            else {
                if(location.row() < min.row())
                    min = location;
                if(location.row() > max.row())
                    max = location;
            }
        }
        return new Pair<>(min, max);
    }

    /**
     * Checks if there is no empty square in the word
     * @param locations list of location
     * @param isHorizontal to check horizontally or vertically
     * @return true if conditions are satisfied, false otherwise
     */
    boolean noEmptySquareInRange(List<Location> locations, boolean isHorizontal) {
        Pair<Location, Location> p = getMaxAndMinElements(locations, isHorizontal);
        Location min = p.getKey();
        Location max = p.getValue();
        if(isHorizontal) {
            for (int i = min.col(); i <= max.col(); i++) {
                if(!locations.contains(new Location(min.row(), i))) {
                    if ((squares[min.row()][i]).getLetterTile() == null)
                        return false;
                }
            }
        }
        else {
            for (int i = min.row(); i <= max.row(); i++) {
                if(!locations.contains(new Location(i, min.col()))) {
                    if ((squares[i][min.col()]).getLetterTile() == null)
                        return false;
                }
            }
        }
    return true;
    }

    /**
     * Checks if any location is the center of the board
     * @param locations list of locations
     * @return true if valid, false otherwise
     */
    boolean anyTileAtCenter(List<Location> locations) {
        return locations.contains(new Location(centerBoard, centerBoard));
    }

    /**
     * Checks if any tile has adjacent tiles
     * @param locations list of locations
     * @return true if there is, false otherwise
     */
    boolean anyExistingTileAdjacent(List<Location> locations) {
        for(Location location: locations) {
            for(Location neighbour: location.neighbours()) {
                if(letterTileAt(neighbour) != null)
                    return true;
            }
        }
        return false;
    }

    /**
     * Gets the square at location
     * shallow copy
     * @param location location
     * @return square
     */
    private Square squareAtLoc(Location location) {
        return squares[location.row()][location.col()];
    }

    /**
     * Gets the square at location
     * deep copy of square
     * @param location location
     * @return square
     */
    public Square squareAt(Location location) {
        Square square = null;
        if(isLocationInBoard(location))
            square = new Square(squares[location.row()][location.col()]);
        return square;
    }

    /**
     * Return the toString representation of the board
     * The entire board can be viewed in command line with this
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(int i = 1; i < squares.length -1; i++) {
            for (int j = 1; j < squares.length -1; j++) {
                result.append(squares[i][j]);
                result.append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * get all letter tiles place on the board
     * @return map of location and letter tiles
     */
    Map<Location, LetterTile> getLetterTileMap() {
        return new HashMap<Location, LetterTile>(letterTileMap);
    }

    /**
     * Returns dimension of the board
     * @return dimension
     */
    public int dimension() {
        return boardDimension;
    }
}
