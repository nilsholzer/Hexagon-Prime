package edu.kit.kastel.model.logic;

import edu.kit.kastel.model.entity.Vector2D;

/**
 * Interface, which contains command methods for the hex game.
 * @author uhquw
 * @version 1.0.0
 */
public interface HexCommands {
    /**
     * Lists a certain amount of the last placed tokens.
     * @param turns The amount of tasks to be shown
     * @return A visualization of the last placed tokens
     */
    String history(int turns);

    /**
     * Places a token on the given coordinates.
     * @param coordinates The coordinates, where the token should be placed
     * @return A confirmation of the successful placement of the token
     */
    String place(Vector2D coordinates);

    /**
     * Prints the game board of a hex game.
     * @return A visualization of the game board
     */
    String print();

    /**
     * Swaps the two players on the first move of the second player.
     * @return A confirmation of the successful swap
     */
    String swap();

}
