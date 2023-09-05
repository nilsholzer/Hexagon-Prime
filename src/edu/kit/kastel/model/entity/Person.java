package edu.kit.kastel.model.entity;

import edu.kit.kastel.model.logic.GameBoard;

public class Person extends Player {
    public Person(final String name, final Hexagon token) {
        super(name, token);
    }

    @Override
    public void place(Vector2D coordinates) {

    }
}
