package edu.cmu.cs.cs214.hw4.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.omg.PortableInterceptor.LOCATION_FORWARD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tushar on 22-Oct-16.
 */
public class BoardTest {
    //CHECKSTYLE:OFF
    Board board;

    @Before
    public void BoardInitTest() {
        board = new Board();
        Assert.assertEquals(board.isEmpty(), true);
        board.toString();
    }

    @Test
    public void LocationTest() {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location(0,0));
        locations.add(new Location(-1,8));
        locations.add(new Location(20,9));

        Assert.assertEquals(board.areValidLocations(locations), false);

        for(Location location: locations) {
            Assert.assertEquals(board.isValidLocation(location), false);
        }
    }

    @Test
    public void TilesHorizontalTest() {
        List<LetterTile> letterTiles = new ArrayList<>();
        letterTiles.add(LetterTile.A);
        letterTiles.add(LetterTile.B);
        letterTiles.add(LetterTile.C);
        List<Location> locations = new ArrayList<>();
        locations.add(new Location(8,7));
        locations.add(new Location(8,8));
        locations.add(new Location(8,9));
        Map<Location, LetterTile> letterTileMap = new HashMap<>();
        Assert.assertEquals(board.anyExistingTileAdjacent(locations), false);
        Assert.assertEquals(board.areValidLocations(locations), true);
        Assert.assertEquals(board.anyTileAtCenter(locations), true);
        Assert.assertEquals(board.areCollinearInRow(locations), true);
        Assert.assertEquals(board.areCollinearInColumn(locations), false);
        int i = 0;
        for(LetterTile letterTile: letterTiles)
            letterTileMap.put(locations.get(i++), letterTile);
        board.placeAllLetterTiles(letterTileMap);

        i = 0;
        for(Location location:locations)
            Assert.assertEquals(board.squareAt(location).getLetterTile(), letterTiles.get(i++));

        Assert.assertEquals(board.isEmpty(), false);

        Assert.assertEquals(board.letterTileAt(locations.get(0)), letterTiles.get(0));
        Assert.assertEquals(board.noEmptySquareInRange(locations, true), true);
        Assert.assertEquals(board.areCollinearAndContinuous(locations), true);

        board.removeAllLetterTiles(locations);
        Assert.assertEquals(board.isEmpty(), true);
    }

    @Test
    public void TilesVerticalTest() {
        List<LetterTile> letterTiles = new ArrayList<>();
        letterTiles.add(LetterTile.C);
        letterTiles.add(LetterTile.D);
        letterTiles.add(LetterTile.E);
        List<Location> locations = new ArrayList<>();
        locations.add(new Location(1,1));
        locations.add(new Location(2,1));
        locations.add(new Location(3,1));
        Map<Location, LetterTile> letterTileMap = new HashMap<>();
        Assert.assertEquals(board.anyExistingTileAdjacent(locations), false);
        Assert.assertEquals(board.anyTileAtCenter(locations), false);
        Assert.assertEquals(board.areCollinearInRow(locations), false);
        Assert.assertEquals(board.areCollinearInColumn(locations), true);
        Assert.assertEquals(board.areValidLocations(locations), true);
        int i = 0;
        for(LetterTile letterTile: letterTiles)
            letterTileMap.put(locations.get(i++), letterTile);
        board.placeAllLetterTiles(letterTileMap);

        i = 0;
        for(Location location:locations)
            Assert.assertEquals(board.squareAt(location).getLetterTile(), letterTiles.get(i++));

        Assert.assertEquals(board.isEmpty(), false);
        Assert.assertEquals(board.letterTileAt(locations.get(0)), letterTiles.get(0));
        Assert.assertEquals(board.noEmptySquareInRange(locations, true), true);
        Assert.assertEquals(board.areCollinearAndContinuous(locations), true);

        board.removeAllLetterTiles(locations);
        Assert.assertEquals(board.isEmpty(), true);
    }

    @Test
    public void BehaviourTest() {
        board = new Board();
        List<LetterTile> letterTiles = new ArrayList<>();
        letterTiles.add(LetterTile.D);
        letterTiles.add(LetterTile.O);
        letterTiles.add(LetterTile.G);
        List<Location> locations = new ArrayList<>();
        locations.add(new Location(7,8));
        locations.add(new Location(8,8));
        locations.add(new Location(9,8));

        int i = 0;
        Map<Location, LetterTile> letterTileMap = new HashMap<>();
        for(LetterTile letterTile: letterTiles)
            letterTileMap.put(locations.get(i++), letterTile);
        board.placeAllLetterTiles(letterTileMap);

        List<LetterTile> testTile = new ArrayList<>();
        testTile.add(LetterTile.N);
        List<Location> testLoc = new ArrayList<>();
        testLoc.add(new Location(8,7));
        Assert.assertEquals(board.anyExistingTileAdjacent(testLoc), true);
        Assert.assertEquals(board.anyTileAtCenter(testLoc), false);
        Assert.assertEquals(board.areCollinearInRow(testLoc), true);
        Assert.assertEquals(board.areCollinearInColumn(testLoc), true);
        Assert.assertEquals(board.areValidLocations(testLoc), true);
    }

    @Test
    public void BehaviourTest2() {
        List<LetterTile> letterTiles = new ArrayList<>();
        letterTiles.add(LetterTile.D);
        letterTiles.add(LetterTile.O);
        letterTiles.add(LetterTile.G);
        letterTiles.add(LetterTile.D);
        letterTiles.add(LetterTile.O);
        letterTiles.add(LetterTile.D);
        List<Location> locations = new ArrayList<>();
        locations.add(new Location(7,8));
        locations.add(new Location(8,8));
        locations.add(new Location(9,8));
        locations.add(new Location(6,7));
        locations.add(new Location(7,7));
        locations.add(new Location(7,9));

        int i = 0;
        Map<Location, LetterTile> letterTileMap = new HashMap<>();
        for(LetterTile letterTile: letterTiles)
            letterTileMap.put(locations.get(i++), letterTile);
        board.placeAllLetterTiles(letterTileMap);

        List<LetterTile> testTile = new ArrayList<>();
        testTile.add(LetterTile.N);
        List<Location> testLoc = new ArrayList<>();
        testLoc.add(new Location(8,7));
        Assert.assertEquals(board.anyExistingTileAdjacent(testLoc), true);
        Assert.assertEquals(board.anyTileAtCenter(testLoc), false);
        Assert.assertEquals(board.areCollinearInRow(testLoc), true);
        Assert.assertEquals(board.areCollinearInColumn(testLoc), true);
        //Assert.assertEquals(board.areValidLocations(testLoc), true);
    }
}
