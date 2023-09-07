package edu.kit.kastel.model.exceptions;

/**
 * Exception for signaling that more moves were asked in the history command, than actual moves are available.
 * @author uhquw
 * @version 1.0.0
 */
public class HistoryException extends IndexOutOfBoundsException {
    private static final String INVALID_TURN = "You cannot look at the last %s moves if the game only had %s moves";

    /**
     * Constructs a new HistoryException with message.
     * @param turns     the amount of turns asked from the player to be shown
     * @param turnsSize the amount of turns available in the game
     */
    public HistoryException(final int turns, final int turnsSize) {
        super(INVALID_TURN.formatted(turns, turnsSize));
    }
}
