package edu.kit.kastel.model.entity;

import java.util.Objects;

public abstract class Player {
    protected Hexagon token;
    private final String name;
    protected Player(String name, Hexagon token) {
        this.name = Objects.requireNonNull(name);
        this.token = token;
    }
    public String getName() {
        return name;
    }
    public Hexagon getToken() {
        return this.token;
    }

    public void swap() {
        if (this.token == Hexagon.RED) {
            this.token = Hexagon.BLUE;
        } else {
            this.token = Hexagon.RED;
        }
    }
    public abstract void place(Vector2D coordinates);

}
