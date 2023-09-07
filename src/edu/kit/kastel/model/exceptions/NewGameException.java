package edu.kit.kastel.model.exceptions;

/**
 * Exception for signaling that a game with the given name already exists.
 * @author uhquw
 * @version 1.0.0
 */
public class NewGameException extends IllegalArgumentException {
    private static final String DUPLICATE_NAME = "There is already a game called %s";

    /**
     * Constructs a new NewGameException with message.
     * @param gameName the doubled name of the game
     */
    public NewGameException(final String gameName) {
        super(DUPLICATE_NAME.formatted(gameName));
    }
}
