package edu.cmu.cs.cs214.hw4.core;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Tushar on 22-Oct-16.
 */
public class BagTest {
    //CHECKSTYLE:OFF
    Random random = new Random(31);
    Bag bag = new Bag(random);
    List<String> rackKnown1 = Arrays.asList("A", "I", "S", "H", "E", "E", "A");
    List<String> rackKnown2 = Arrays.asList("A", "I", "H", "E", "E", "S", "V");
    List<String> rackKnown3 = Arrays.asList("R", "O", "A", "I", "T", "C", "L");

    @Test
    public void bagInitializationTest() {
        Assert.assertEquals(bag.isEmpty(), false);
    }

    @Test
    public void rackReplenishTest() {
        List<LetterTile> rack = new ArrayList<>();
        bag.replenishTiles(rack);
        Assert.assertEquals(rack.size(), 7);
        int i = 0;
        for(LetterTile letterTile: rack){
            Assert.assertEquals(letterTile.letter(), rackKnown1.get(i++));
        }
        rack.remove(2);
        rack.remove(5);
        bag.replenishTiles(rack);
        Assert.assertEquals(rack.size(), 7);
        i = 0;
        for(LetterTile letterTile: rack){
            Assert.assertEquals(letterTile.letter(), rackKnown2.get(i++));
        }
    }

    @Test
    public void exchangeTileTest() {
        List<LetterTile> rack = new ArrayList<>();
        bag.replenishTiles(rack);
        List<LetterTile> newRack = bag.exchangeTiles(rack);
        Assert.assertEquals(rack.size(), 7);
        int i = 0;
        for(LetterTile letterTile: newRack){
            Assert.assertEquals(letterTile.letter(), rackKnown3.get(i++));
        }
    }
}
