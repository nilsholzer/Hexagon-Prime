package edu.kit.kastel.model.exceptions;

/**
 * Exception for signaling that a player cannot execute a basic command.
 * @author uhquw
 * @version 1.0.0
 */
public class BasicCommandException extends IllegalArgumentException {
    /**
     * Constructs a new BasicCommandException.
     * @param message the message of the BasicCommandException
     */
    public BasicCommandException(final String message) {
        super(message);
    }
}
