package edu.cmu.cs.cs214.hw4.gui;

import edu.cmu.cs.cs214.hw4.core.SquareType;

import java.awt.Color;

/**
 * Represents the Color of the square types
 */
public enum SquareTypeColor {
    RegularSquare(SquareType.RegularSquare, Color.LIGHT_GRAY),
    DoubleLetterSquare(SquareType.DoubleLetterSquare, Color.CYAN),
    TripleLetterSquare(SquareType.TripleLetterSquare, Color.BLUE),
    DoubleWordSquare(SquareType.DoubleWordSquare, Color.PINK),
    TripleWordSquare(SquareType.TripleWordSquare, Color.RED);


    private final Color colour;
    private final SquareType squareT;

    /**
     * private constructor
     * @param squareT square type
     * @param colour color
     */
    SquareTypeColor(SquareType squareT, Color colour) {
        this.squareT = squareT;
        this.colour = colour;
    }

    /**
     * returns the color corresponding to the square type
     * @return color
     */
    Color color() {
        return colour;
    }

    /**
     * Returns the squaretype
     * @return squartype
     */
    SquareType squareType() {
        return squareT;
    }
}
