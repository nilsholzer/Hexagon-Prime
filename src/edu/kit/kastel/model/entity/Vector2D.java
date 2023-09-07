package edu.kit.kastel.model.entity;

/**
 * This class describes a 2D vector consisting of a x and y coordinate.
 * @author uhquw
 * @version 1.0.0
 */
public class Vector2D {
    private final int xPos;
    private final int yPos;

    /**
     * Constructs a new Vector2D.
     * @param xPos the x coordinate of the vector
     * @param yPos the y coordinate of the vector
     */
    public Vector2D(final int xPos, final int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * Gets the x coordinate of the vector.
     * @return the x coordinate
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * Gets the y coordinate of the vector.
     * @return the y coordinate
     */
    public int getyPos() {
        return yPos;
    }
}
