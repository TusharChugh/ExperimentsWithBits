package edu.cmu.cs.cs214.hw4.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * Created by Tushar on 22-Oct-16.
 */
public class SpecialTileTest {
    private static Game game;
    private static Set<String> words;
    private static final String dictionaryPath = "src/main/resources/words.txt";
    private static final Random random = new Random(31);
    List<String> playerName = Arrays.asList("One", "Two");
    Location loc;
    LetterTile letterTile ;
    Map<Location, LetterTile> map;
    @Before
    public void startGame() {
        try{
            words =  DictionaryHelper.listOfWords(dictionaryPath);
        }
        catch (IOException e) {
            //Test in dictionary test, so ignore here
        }
        game = new Game(playerName, words, random);
        game.startGame();
        Assert.assertEquals(game.currentPlayer().name(), "One");
        Assert.assertEquals(game.currentPlayer().score(), 0);
       // Assert.assertEquals(game.hasGameEnded(), false);

        loc = new Location(8,8);
        letterTile = LetterTile.D;
        map = new HashMap<>();
        map.put(loc,letterTile);
    }

    @Test (expected = IllegalArgumentException.class)
    public void insufficientPoints() {
        game.currentPlayer().addOrSubtractScore(0, true);
        Assert.assertEquals(game.currentPlayer().score(), 0);
        SpecialTile specialTile = game.purchaseSpecialTile(SpecialTileType.ReverseOrder);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().size(), 0);
    }

    @Test
    public void priceStringandSanityCheck() {
        game.currentPlayer().addOrSubtractScore(50, true);
        Assert.assertEquals(game.currentPlayer().score(), 50);
        SpecialTile specialTile = game.purchaseSpecialTile(SpecialTileType.ReverseOrder);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().size(), 1);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().get(0).specialTileType(), SpecialTileType.ReverseOrder);
        Assert.assertEquals(game.currentPlayer().score(), 50- SpecialTileType.ReverseOrder.price());
        Assert.assertEquals(specialTile.toString(), SpecialTileType.ReverseOrder.toString());
    }

    @Test
    public void addingandremovingspecialtiles() {
        game.currentPlayer().addOrSubtractScore(50, true);
        Assert.assertEquals(game.currentPlayer().score(), 50);
        SpecialTile specialTile = game.purchaseSpecialTile(SpecialTileType.ReverseOrder);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().size(), 1);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().get(0).specialTileType(), SpecialTileType.ReverseOrder);
        Assert.assertEquals(game.currentPlayer().score(), 50- SpecialTileType.ReverseOrder.price());
        Assert.assertEquals(specialTile.toString(), SpecialTileType.ReverseOrder.toString());
        game.pass();
        game.pass();
        game.placeSpecialTile(specialTile, new Location(8,8));
        Assert.assertEquals(game.getBoard().squareAt(new Location(8,8)).getSpecialTiles().size() , 1);
        game.getBoard().squareAt(new Location(8,8)).removeAllSpecialTiles();
        Assert.assertEquals(game.getBoard().squareAt(new Location(8,8)).getSpecialTiles().size() , 0);
    }


    @Test (expected = IllegalArgumentException.class)
    public void activatinginsameturn() {
        game.currentPlayer().addOrSubtractScore(50, true);
        Assert.assertEquals(game.currentPlayer().score(), 50);
        SpecialTile specialTile = game.purchaseSpecialTile(SpecialTileType.ReverseOrder);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().size(), 1);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().get(0).specialTileType(), SpecialTileType.ReverseOrder);
        Assert.assertEquals(game.currentPlayer().score(), 50- SpecialTileType.ReverseOrder.price());
        Assert.assertEquals(specialTile.toString(), SpecialTileType.ReverseOrder.toString());
        game.pass();
        game.placeSpecialTile(specialTile, new Location(8,8));
    }

    @Test
    public void activatingReverseOrder()
    {
        game.currentPlayer().addOrSubtractScore(50, true);
        Assert.assertEquals(game.currentPlayer().score(), 50);
        SpecialTile specialTile = game.purchaseSpecialTile(SpecialTileType.ReverseOrder);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().size(), 1);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().get(0).specialTileType(), SpecialTileType.ReverseOrder);
        Assert.assertEquals(game.currentPlayer().score(), 50- SpecialTileType.ReverseOrder.price());
        Assert.assertEquals(specialTile.toString(), SpecialTileType.ReverseOrder.toString());
        game.pass();
        game.pass();
        game.placeSpecialTile(specialTile, new Location(8,8));
        Assert.assertEquals(game.getBoard().squareAt(new Location(8,8)).getSpecialTiles().size() , 1);
        game.pass();
        map.clear();
        map.put(loc, game.currentPlayer().getRack().get(1));
        map.put(loc.right(), game.currentPlayer().getRack().get(2));
        game.placeLetterTiles(map);
        Assert.assertEquals(game.currentPlayer().name(), "One");
    }


    @Test
    public void activatingtenPoints()
    {
        game.currentPlayer().addOrSubtractScore(50, true);
        Assert.assertEquals(game.currentPlayer().score(), 50);
        SpecialTile specialTile = game.purchaseSpecialTile(SpecialTileType.TenExtraPoints);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().size(), 1);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().get(0).specialTileType(), SpecialTileType.TenExtraPoints);
        Assert.assertEquals(game.currentPlayer().score(), 50- SpecialTileType.TenExtraPoints.price());
        Assert.assertEquals(specialTile.toString(), SpecialTileType.TenExtraPoints.toString());
        game.pass();
        game.pass();
        game.placeSpecialTile(specialTile, new Location(8,8));
        Assert.assertEquals(game.getBoard().squareAt(new Location(8,8)).getSpecialTiles().size() , 1);
        game.pass();
        map.clear();
        map.put(loc, game.currentPlayer().getRack().get(1));
        map.put(loc.right(), game.currentPlayer().getRack().get(2));
        int expectedScore = (2*((game.currentPlayer().getRack().get(1)).points() + (game.currentPlayer().getRack().get(2)).points())) + 10;
        game.placeLetterTiles(map);
        game.pass();
        Assert.assertEquals(game.currentPlayer().score(), expectedScore);
    }

    @Test
    public void activatingNegativePoints()
    {
        game.currentPlayer().addOrSubtractScore(50, true);
        Assert.assertEquals(game.currentPlayer().score(), 50);
        SpecialTile specialTile = game.purchaseSpecialTile(SpecialTileType.TenExtraPoints);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().size(), 1);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().get(0).specialTileType(), SpecialTileType.TenExtraPoints);
        Assert.assertEquals(game.currentPlayer().score(), 50- SpecialTileType.TenExtraPoints.price());
        Assert.assertEquals(specialTile.toString(), SpecialTileType.TenExtraPoints.toString());
        game.pass();
        game.pass();
        game.placeSpecialTile(specialTile, new Location(8,8));
        Assert.assertEquals(game.getBoard().squareAt(new Location(8,8)).getSpecialTiles().size() , 1);
        game.pass();
        map.clear();
        map.put(loc, game.currentPlayer().getRack().get(1));
        map.put(loc.right(), game.currentPlayer().getRack().get(2));
        game.placeLetterTiles(map);
        game.pass();
    }

    @Test
    public void activatingBoom()
    {
        game.currentPlayer().addOrSubtractScore(50, true);
        Assert.assertEquals(game.currentPlayer().score(), 50);
        SpecialTile specialTile = game.purchaseSpecialTile(SpecialTileType.Boom);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().size(), 1);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().get(0).specialTileType(), SpecialTileType.Boom);
        Assert.assertEquals(game.currentPlayer().score(), 50- SpecialTileType.Boom.price());
        Assert.assertEquals(specialTile.toString(), SpecialTileType.Boom.toString());
        game.pass();
        game.pass();
        game.placeSpecialTile(specialTile, new Location(8,8));
        Assert.assertEquals(game.getBoard().squareAt(new Location(8,8)).getSpecialTiles().size() , 1);
        game.pass();
        map.clear();
        map.put(loc, game.currentPlayer().getRack().get(1));
        map.put(loc.right(), game.currentPlayer().getRack().get(2));
        game.placeLetterTiles(map);
        game.pass();
        Assert.assertEquals(game.getBoard().squareAt(loc).getLetterTile(), null);
    }

    @Test
    public void activatingNegative()
    {
        game.currentPlayer().addOrSubtractScore(50, true);
        Assert.assertEquals(game.currentPlayer().score(), 50);
        SpecialTile specialTile = game.purchaseSpecialTile(SpecialTileType.NegativePoints);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().size(), 1);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().get(0).specialTileType(), SpecialTileType.NegativePoints);
        Assert.assertEquals(game.currentPlayer().score(), 50- SpecialTileType.NegativePoints.price());
        Assert.assertEquals(specialTile.toString(), SpecialTileType.NegativePoints.toString());
        game.pass();
        game.pass();
        game.placeSpecialTile(specialTile, new Location(8,8));
        Assert.assertEquals(game.getBoard().squareAt(new Location(8,8)).getSpecialTiles().size() , 1);
        game.pass();
        map.clear();
        map.put(loc, game.currentPlayer().getRack().get(1));
        map.put(loc.right(), game.currentPlayer().getRack().get(2));
        int previousScore = (game.currentPlayer().getRack().get(1)).points() * 2 + (game.currentPlayer().getRack().get(2)).points();
        game.placeLetterTiles(map);
        game.pass();
        Assert.assertEquals(game.currentPlayer().score() < 0, true);
    }

    @Test
    public void negpurchase() {
        game.currentPlayer().addOrSubtractScore(50, true);
        Assert.assertEquals(game.currentPlayer().score(), 50);
        SpecialTile specialTile2 = game.purchaseSpecialTile(SpecialTileType.NegativePoints);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().size(), 1);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().get(0).specialTileType(), SpecialTileType.NegativePoints);
        Assert.assertEquals(game.currentPlayer().score(), 50- SpecialTileType.NegativePoints.price());
        Assert.assertEquals(specialTile2.toString(), SpecialTileType.NegativePoints.toString());
        game.pass();
        game.pass();
        game.placeSpecialTile(specialTile2, new Location(8,8));
        Assert.assertEquals(game.getBoard().squareAt(new Location(8,8)).getSpecialTiles().size() , 1);
    }

    @Test
    public void tenpointspurchase() {
        game.currentPlayer().addOrSubtractScore(50, true);
        Assert.assertEquals(game.currentPlayer().score(), 50);
        SpecialTile specialTile3 = game.purchaseSpecialTile(SpecialTileType.TenExtraPoints);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().size(), 1);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().get(0).specialTileType(), SpecialTileType.TenExtraPoints);
        Assert.assertEquals(game.currentPlayer().score(), 50- SpecialTileType.TenExtraPoints.price());
        Assert.assertEquals(specialTile3.toString(), SpecialTileType.TenExtraPoints.toString());
        game.pass();
        game.pass();
        game.placeSpecialTile(specialTile3, new Location(8,8));
        Assert.assertEquals(game.getBoard().squareAt(new Location(8,8)).getSpecialTiles().size() , 1);
    }

    @Test
    public void boomPlace() {
        game.currentPlayer().addOrSubtractScore(50, true);
        Assert.assertEquals(game.currentPlayer().score(), 50);
        SpecialTile specialTile4 = game.purchaseSpecialTile(SpecialTileType.Boom);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().size(), 1);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().get(0).specialTileType(), SpecialTileType.Boom);
        Assert.assertEquals(game.currentPlayer().score(), 50- SpecialTileType.Boom.price());
        Assert.assertEquals(specialTile4.toString(), SpecialTileType.Boom.toString());
        game.pass();
        game.pass();
        game.placeSpecialTile(specialTile4, new Location(8,8));
        Assert.assertEquals(game.getBoard().squareAt(new Location(8,8)).getSpecialTiles().size() , 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void Illegalarg() {
        game.currentPlayer().addOrSubtractScore(50, true);
        Assert.assertEquals(game.currentPlayer().score(), 50);
        SpecialTile specialTile4 = game.purchaseSpecialTile(SpecialTileType.Boom);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().size(), 1);
        Assert.assertEquals(game.currentPlayer().getSpecialRack().get(0).specialTileType(), SpecialTileType.Boom);
        Assert.assertEquals(game.currentPlayer().score(), 50- SpecialTileType.Boom.price());
        Assert.assertEquals(specialTile4.toString(), SpecialTileType.Boom.toString());
        game.pass();
        game.placeSpecialTile(specialTile4, new Location(8,8));
        Assert.assertEquals(game.getBoard().squareAt(new Location(8,8)).getSpecialTiles().size() , 1);
    }
}
