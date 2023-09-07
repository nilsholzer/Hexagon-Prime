package edu.kit.kastel.model.exceptions;

public class PlaceException extends IllegalArgumentException {
    private static final String NOT_PLACEABLE = "There is already a token on (%d, %d)";
    private static final String CANNOT_PLACE_AFTER_WIN = "You cannot place another hexagon, if a player has already won";

    public PlaceException(final int row, final int column) {
        super(NOT_PLACEABLE.formatted(row, column));
    }
    public PlaceException() {
        super(CANNOT_PLACE_AFTER_WIN);
    }
}
