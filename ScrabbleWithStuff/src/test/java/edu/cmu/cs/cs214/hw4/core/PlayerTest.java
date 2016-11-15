package edu.cmu.cs.cs214.hw4.core;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tushar on 22-Oct-16.
 */
public class PlayerTest {
    //CHECKSTYLE:OFF
    private String playerName1 = "Tushar";
    private String playerName2 = "Player";
    private Player player1 = new Player(playerName1, 0);
    private Player player2 = new Player(playerName2, 1);

    List<LetterTile> letterTiles = new ArrayList<>();

    @Test
    public void initTest() {
        Assert.assertEquals(player1.toString(), playerName1);
        Assert.assertEquals(player1.name(), playerName1);
        Assert.assertEquals(player1.score(), 0);
        Assert.assertEquals(player1.getRack(), new ArrayList());
    }

    @Test
    public void rackTest() {
        letterTiles.add(LetterTile.A);
        letterTiles.add(LetterTile.B);
        letterTiles.add(LetterTile.C);
        player2.setRack(letterTiles);
        Assert.assertEquals(letterTiles, player2.getRack());
    }

    @Test
    public void scoreTest() {
        player1.addOrSubtractScore(5, true);
        Assert.assertEquals(player1.score(), 5);
        player1.addOrSubtractScore(2, false);
        Assert.assertEquals(player1.score(), 3);
    }

    @Test
    public void equalsTest() {
        Assert.assertEquals(player1.equals(player2), false);
        Player player3 = new Player("Tushar", 0);
        Assert.assertEquals(player3.equals(player1), true);
    }
}
