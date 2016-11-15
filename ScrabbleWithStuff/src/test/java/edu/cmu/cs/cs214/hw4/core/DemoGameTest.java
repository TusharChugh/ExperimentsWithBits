package edu.cmu.cs.cs214.hw4.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.*;

/**
 * Created by Tushar on 22-Oct-16.
 */
public class DemoGameTest {
    //CHECKSTYLE:OFF
    private static Game game;
    private static Set<String> words;
    private static final String dictionaryPath = "src/main/resources/words.txt";
    private static final Random random = new Random(31);
    List<String> playerName = Arrays.asList("One", "Two");
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
    }

    @Test
    public void demoGameTest() {
        Map<Integer, LetterTile> tileToExchange = new LinkedHashMap<>();
        List<LetterTile> rack = game.currentPlayer().getRack();
        tileToExchange.put(3, rack.get(3));
        tileToExchange.put(5, rack.get(5));
        game.exchangeTiles(tileToExchange);
        Map<Location, LetterTile> letterTilesMap = new HashMap<>();
        letterTilesMap.put(new Location(8,7), game.currentPlayer().getRack().get(1));
        letterTilesMap.put(new Location(8,8), game.currentPlayer().getRack().get(2));
        letterTilesMap.put(new Location(8,9), game.currentPlayer().getRack().get(3));
        game.placeLetterTiles(letterTilesMap);
    }

    @Test (expected = IllegalArgumentException.class)
    public void invalidInputTest() {
        Map<Location, LetterTile> letterTilesMap1 = new HashMap<>();
        letterTilesMap1.put(new Location(8,1), game.currentPlayer().getRack().get(1));
        letterTilesMap1.put(new Location(8,2), game.currentPlayer().getRack().get(2));
        game.placeLetterTiles(letterTilesMap1);
    }

    @Test
    public void challengeTest() {
        Map<Location, LetterTile> letterTilesMap = new HashMap<>();
        letterTilesMap.put(new Location(8,7), game.currentPlayer().getRack().get(4));
        letterTilesMap.put(new Location(8,8), game.currentPlayer().getRack().get(5));
        letterTilesMap.put(new Location(8,9), game.currentPlayer().getRack().get(0));
        letterTilesMap.put(new Location(8,10), game.currentPlayer().getRack().get(6));
        game.placeLetterTiles(letterTilesMap);
        game.challenge(game.currentPlayer());
    }

    @Test
    public void getPlayersTest() {
        List<Player> players = game.getPlayers();
        Assert.assertEquals(players.size(), 2);
        Assert.assertEquals(players.get(0).id(), 0);
        Assert.assertEquals(players.get(1).id(), 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void nonCollinearTest() {
        Map<Location, LetterTile> letterTilesMap = new HashMap<>();
        letterTilesMap.put(new Location(8,7), game.currentPlayer().getRack().get(4));
        letterTilesMap.put(new Location(8,11), game.currentPlayer().getRack().get(5));
        letterTilesMap.put(new Location(8,9), game.currentPlayer().getRack().get(0));
        letterTilesMap.put(new Location(8,10), game.currentPlayer().getRack().get(6));
        game.placeLetterTiles(letterTilesMap);
    }

    @Test (expected = IllegalArgumentException.class)
    public void notInSamelineTest() {
        Map<Location, LetterTile> letterTilesMap = new HashMap<>();
        letterTilesMap.put(new Location(8,7), game.currentPlayer().getRack().get(4));
        letterTilesMap.put(new Location(7,8), game.currentPlayer().getRack().get(5));
        letterTilesMap.put(new Location(8,9), game.currentPlayer().getRack().get(0));
        letterTilesMap.put(new Location(8,10), game.currentPlayer().getRack().get(6));
        game.placeLetterTiles(letterTilesMap);
    }
}
