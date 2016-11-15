package edu.cmu.cs.cs214.hw4.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tushar on 22-Oct-16.
 */
public class SquareTest {
    //CHECKSTYLE:OFF
    List<Location> loc = new ArrayList<>();
    List<Square> squares = new ArrayList<>();

    @Before
    public void initTest() {
        loc.add(new Location(1,1));
        loc.add(new Location(2,2));
        loc.add(new Location(3,3));
        loc.add(new Location(8,8));
        loc.add(new Location(4,4));

        int i = 0;
        squares.add(new Square(SquareType.RegularSquare, loc.get(i++)));
        squares.add(new Square(SquareType.DoubleLetterSquare, loc.get(i++)));
        squares.add(new Square(SquareType.TripleLetterSquare, loc.get(i++)));
        squares.add(new Square(SquareType.DoubleWordSquare, loc.get(i++)));
        squares.add(new Square(SquareType.TripleWordSquare, loc.get(i++)));

        i = 0;
        Assert.assertEquals(squares.get(i++).toString(), "RG");
        Assert.assertEquals(squares.get(i++).toString(), "DL");
        Assert.assertEquals(squares.get(i++).toString(), "TL");
        Assert.assertEquals(squares.get(i++).toString(), "DW");
        Assert.assertEquals(squares.get(i++).toString(), "TW");

        i = 0;
        for(Square square: squares) {
            Assert.assertEquals(square.location(), loc.get(i++));
        }

        for(Square square: squares) {
            Assert.assertEquals(square.getLetterTile(), null);
        }

        i = 0;
        Assert.assertEquals(SquareType.RegularSquare, squares.get(i++).squareType());
        Assert.assertEquals(SquareType.DoubleLetterSquare, squares.get(i++).squareType());
        Assert.assertEquals(SquareType.TripleLetterSquare, squares.get(i++).squareType());
        Assert.assertEquals(SquareType.DoubleWordSquare, squares.get(i++).squareType());
        Assert.assertEquals(SquareType.TripleWordSquare, squares.get(i++).squareType());
    }

    @Test
    public void copyConstructorTest() {
        Square copy = new Square(squares.get(0));
        Assert.assertEquals(copy.location(), squares.get(0).location());
        Assert.assertEquals(copy.getLetterTile(), squares.get(0).getLetterTile());
        Assert.assertEquals(copy.toString(), squares.get(0).toString());
        Assert.assertEquals(copy.squareType(), squares.get(0).squareType());
    }

    @Test (expected = NullPointerException.class)
    public void nullTestsSet() {
        squares.get(0).placeLetterTile(null);
    }

    @Test
    public void squareTilesTest() {
        squares.get(0).placeLetterTile(LetterTile.A);
        Assert.assertEquals(squares.get(0).getLetterTile(), LetterTile.A);
        squares.get(0).placeLetterTile(LetterTile.A);
        squares.get(0).removeLetterTile();
        Assert.assertNull(squares.get(0).getLetterTile());
    }


}
