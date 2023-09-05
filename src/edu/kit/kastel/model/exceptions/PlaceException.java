package edu.kit.kastel.model.exceptions;

public class PlaceException extends IllegalArgumentException {
    private static final String NOT_PLACEABLE = "There is already a token on (%d, %d)";

    public PlaceException(final int row, final int column) {
        super(NOT_PLACEABLE.formatted(row, column));
    }
}
