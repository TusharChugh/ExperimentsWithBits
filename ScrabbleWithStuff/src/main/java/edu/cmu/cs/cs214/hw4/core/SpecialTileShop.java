package edu.cmu.cs.cs214.hw4.core;

/**
 * Represents a specialtile shop from where the tiles can be purchased
 * and price and description can be queried
 * @param <T> A type of specialtile
 */
class SpecialTileShop <T extends SpecialTile> {
    private final boolean sub = false;

    /**
     * Constructor to create the instance of the shop
     * As we don't have any limits on quantity this constructor is empty
     */
    public SpecialTileShop(){ }

    /**
     * Function to purchase the special tile
     * @param specialTileType Which special tile to purchase
     * @param owner Player who is purchasing the tile
     * @return the special tile
     */
    T purchase(SpecialTileType specialTileType, Player owner){
        if(specialTileType.price() <= owner.score()) {
            owner.addOrSubtractScore(specialTileType.price(), sub);
            T specialTile;
            switch (specialTileType){
                case ReverseOrder:
                    specialTile = (T) new ReverseOrderSpecialTile();
                    break;
                case NegativePoints:
                    specialTile = (T) new NegativePointsSpecialTile();
                    break;
                case Boom:
                    specialTile = (T) new BoomSpecialTile();
                    break;
                case TenExtraPoints:
                    specialTile = (T) new TenExtraPointsSpecialTile();
                    break;
                case RotateDown:
                    specialTile = (T) new RotateDownSpecialTile();
                    break;
                default:
                    throw new IllegalArgumentException("Tile not available");
            }

            owner.addSpecialTile(specialTile);
            return specialTile;
        }
        else
            throw new IllegalArgumentException("Not Enough Money");
    }

    /**
     * Method to query the price of a special tile type
     * @param specialTileType special tile type
     * @return price of special tile type
     */
    int price(SpecialTileType specialTileType ){
        return specialTileType.price();
    }

    /**
     * Desciption of a special tile type
     * @param specialTileType special tile type
     * @return description
     */
    String description(SpecialTileType specialTileType){
        return specialTileType.description();
    }
}
