package edu.cmu.cs.cs214.hw4.core;

/**
 * Represents types of special tiles
 */
public enum SpecialTileType {
    ReverseOrder("RO", "Reverses the order of play", 5),
    NegativePoints("NP", "Word negatively score for player who activates this", 7),
    Boom("BM", "3 Tile radius on the board are removed", 15),
    TenExtraPoints("TP","Ten extra points for the player who activates this", 2),
    RotateDown("RD"," each tile on the board shifts down one position", 10);

    //Symbol of the tiles
    private final String symbol;
    //Short description of the tiles
    private final String description;
    //Price value of the tiles
    private final int priceVal;

    /**
     * Contructor
     * @param symbol symbol
     * @param description short descriptipn
     * @param priceVal price
     */
    SpecialTileType(String symbol, String description, int priceVal) {
        this.symbol = symbol;
        this.description = description;
        this.priceVal = priceVal;
    }

    /**
     * the price of the special tile
     * @return the price of the special tile
     */
    public int price() {
        return priceVal;
    }

    /**
     * Returns the description
     * @return short description
     */
    public String description() {
        return description;
    }

    /**
     * Returns the symbol as tostring representation
     * @return symbol as to string
     */
    @Override
    public String toString() {
        return symbol;
    }
}
