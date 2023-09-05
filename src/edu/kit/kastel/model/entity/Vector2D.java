package edu.kit.kastel.model.entity;

public class Vector2D {
    private final int xPos;
    private final int yPos;
    public Vector2D(final int xPos, final int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }
}
