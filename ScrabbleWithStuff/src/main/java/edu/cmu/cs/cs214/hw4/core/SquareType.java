package edu.cmu.cs.cs214.hw4.core;

/**
 * Represents five type of squares which are in scrabble game
 * Contains information about if it is a letter or a word multiplier
 * Also about the multiplicity
 */
public enum SquareType {
    RegularSquare("RG", MultiplierType.LETTERMULTIPLIER, 1),
    DoubleLetterSquare("DL", MultiplierType.LETTERMULTIPLIER, 2),
    TripleLetterSquare("TL", MultiplierType.LETTERMULTIPLIER, 3),
    DoubleWordSquare("DW", MultiplierType.WORDMULTIPLIER, 2),
    TripleWordSquare("TW", MultiplierType.WORDMULTIPLIER, 3);

    /**
     * Multiplier type
     */
    enum MultiplierType {LETTERMULTIPLIER, WORDMULTIPLIER}

    //Type of multiplier
    private MultiplierType multiplierType;
    //Symbol of the Square
    private final String symbol;
    //Multiplicity
    private final int multiplierValue;

    /**
     * Constructor
     * @param symbol symbol of the tile
     * @param multiplierType multiplier type
     * @param multiplierValue multiplicity
     */
    SquareType(String symbol, MultiplierType multiplierType, int multiplierValue) {
        this.symbol = symbol;
        this.multiplierType = multiplierType;
        this.multiplierValue = multiplierValue;
    }

    /**
     * Value of the multiplier
     * @return multiplier value
     */
    int multiplier() {
        return multiplierValue;
    }

    /**
     * Word of letter multiplie
     * @return multiplier type
     */
    MultiplierType multiplierType() {
        return multiplierType;
    }

    /**
     * Return string representation of symbol of the square
     * @return symbol
     */
    @Override
    public String toString() {
        return symbol;
    }
}
