package edu.kit.kastel.model.exceptions;

/**
 * Exception for signaling that the Hex game couldn't be built.
 * @author uhquw
 * @version 1.0.0
 */
public class BuildException extends IllegalArgumentException {
    /**
     * Constructs a new BuildException with message.
     * @param message the reason, why the Hex game couldn't be built
     */
    public BuildException(final String message) {
        super(message);
    }
}

