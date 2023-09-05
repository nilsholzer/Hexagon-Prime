package edu.kit.kastel.model.exceptions;

public class HistoryException extends IndexOutOfBoundsException {
    private static final String INVALID_TURN = "You cannot look at the last %s moves if the game only had %s moves";

    public HistoryException(final int turns, final int turnsSize) {
        super(INVALID_TURN.formatted(turns, turnsSize));
    }
}
