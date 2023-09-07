package edu.kit.kastel.model.exceptions;

/**
 * Exception for signaling that a player is unable to place a token.
 * @author uhquw
 * @version 1.0.0
 */
public class PlaceException extends IllegalArgumentException {
    private static final String NOT_PLACEABLE = "There is already a token on (%d, %d)";
    private static final String CANNOT_PLACE_AFTER_WIN = "You cannot place another hexagon, if a player has already won";

    /**
     * Constructs a new PlaceException, when the hexagon on the given coordinates is already occupied.
     * @param row    the row of the hexagon
     * @param column the column of the hexagon
     */
    public PlaceException(final int row, final int column) {
        super(NOT_PLACEABLE.formatted(row, column));
    }

    /**
     * Constructs a new PlaceException, when a player has already won a game.
     */
    public PlaceException() {
        super(CANNOT_PLACE_AFTER_WIN);
    }
}
