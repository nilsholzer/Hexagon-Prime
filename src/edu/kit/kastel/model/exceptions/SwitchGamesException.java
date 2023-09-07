package edu.kit.kastel.model.exceptions;

/**
 * Exception for signaling that games cannot be switched, because a game with the given name does not exist.
 * @author uhquw
 * @version 1.0.0
 */
public class SwitchGamesException extends IllegalArgumentException {
    private static final String INVALID_NAME_MESSAGE = "A game with that name does not exist";

    /**
     * Constructs a new SwitchGameException with message.
     */
    public SwitchGamesException() {
        super(INVALID_NAME_MESSAGE);
    }
}
