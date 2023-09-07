package edu.kit.kastel.model.exceptions;

/**
 * Exception for signaling that a player cannot swap at this turn.
 * @author uhquw
 * @version 1.0.0
 */
public class SwapException extends IllegalArgumentException {
    private static final String SWAP_ERROR_MESSAGE = "You cannot swap players this turn";

    /**
     * Constructs a new SwapException with message.
     */
    public SwapException() {
        super(SWAP_ERROR_MESSAGE);
    }
}
