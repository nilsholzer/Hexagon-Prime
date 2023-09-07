package edu.kit.kastel.model.entity;

import java.util.Objects;

/**
 * This class describes a player of the game.
 * @author uhquw
 * @version 1.0.0
 */
public class Player {
    private Hexagon token;
    private final String name;

    /**
     * Constructs a player of the game.
     * @param name  the name of the player
     * @param token the token the player can place on the game board
     */
    public Player(String name, Hexagon token) {
        this.name = Objects.requireNonNull(name);
        this.token = token;
    }

    /**
     * Returns the name of the player.
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the token the player can place on the board.
     * @return the token the player can place
     */
    public Hexagon getToken() {
        return this.token;
    }

    /**
     * Swaps the token of the player.
     */
    public void swap() {
        if (this.token == Hexagon.RED) {
            this.token = Hexagon.BLUE;
        } else {
            this.token = Hexagon.RED;
        }
    }
}
