package edu.kit.kastel.model.logic;

/**
 * An interface that indicates, that all its implementations are executable objects, which can be active or not.
 * @author uhquw
 * @version 1.0.0
 */
public interface Executable {
    /**
     * Checks if the object is running or not.
     * @return A boolean telling if it is running
     */
    boolean isActive();

    /**
     * Makes the object quit, so that it is not active anymore.
     */
    void quit();
}
