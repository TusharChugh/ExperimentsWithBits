package edu.cmu.cs.cs214.hw4.core;

/**
 * Representation of all the Letter Tiles in Scrabble Game
 * It contains the information about :
 * 1. Letter
 * 2. Points of the letter
 * 3. How many tiles are there for a letter (multiplicity)
 */
public enum LetterTile {
    A(1, 9), B(3, 2), C(3, 2), D(2, 4), E(1, 12), F(4, 2), G(2, 3), H(4, 2), I(1, 9), J(8, 1), K(5, 1), L(1, 4),
    M(3, 2), N(1, 6), O(1, 8), P(3, 2), Q(10, 1), R(1, 6), S(1, 4), T(1, 6), U(1, 4), V(4, 2),
    W(4, 2), X(8, 1), Y(4, 2), Z(10, 1);

    private final int pointsVal;
    private final int multiplicityVal;

    /**
     *
     * @param pointsVal points associated with the letter tile
     * @param multiplicityVal number of letter tiles
     */
    LetterTile(int pointsVal, int multiplicityVal) {
        this.pointsVal = pointsVal;
        this.multiplicityVal = multiplicityVal;
    }

    /**
     * The letter as in scrabble game
     * @return The letter
     */
    public String letter() {
        return name();
    }

    /**
     * Points
     * @return points associated with the letter tile
     */
    public int points() {
        return pointsVal;
    }

    /**
     * Multiplicity
     * @return multiplicity: Number of letter tiles with the letter type
     */
    public int multiplicity() { return multiplicityVal;}

    /**
     * String representation
     * Format [letter][points]
     * @return string representation
     */
    @Override
    public String toString() {
        return name() + pointsVal;
    }
}
