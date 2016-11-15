package edu.cmu.cs.cs214.hw4.core;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Tushar on 22-Oct-16.
 */
public class LetterTileTest {
    //CHECKSTYLE:OFF


    @Test
    public void letterTileText() {
        LetterTile letterTile= LetterTile.A;
        Assert.assertEquals(letterTile.toString() , "A1");
        Assert.assertEquals(letterTile.letter(), "A");
        Assert.assertEquals(letterTile.multiplicity(), 9);
        Assert.assertEquals(letterTile.points(), 1);
    }

}
