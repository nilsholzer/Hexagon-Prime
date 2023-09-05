package edu.kit.kastel.model.entity;

/**
 * An enumeration that represents the token on the game board.
 * @author uhquw
 * @version 1.0.0
 */
public enum Hexagon {
    /**
     * Represents an empty hexagon on which stone can still be placed.
     */
    PLACEABLE("."),
    /**
     * Represents a hexagon, where the red player has placed a token.
     */
    RED("X"),
    /**
     * Represents a hexagon, where the blue player has placed a token.
     */
    BLUE("O"),
    /**
     * Represents the hexagons, which were used for the winner´s path.
     */
    WINNER("*");

    private final String symbol;
    Hexagon(final String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the symbol of the enumeration.
     * @return the symbol of the enumeration
     */
    public String getSymbol() {
        return this.symbol;
    }
}
