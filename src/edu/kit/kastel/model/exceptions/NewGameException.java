package edu.kit.kastel.model.exceptions;

public class NewGameException extends IllegalArgumentException {
    private static final String DUPLICATE_NAME = "There is already a game called %s";

    public NewGameException(final String gameName) {
        super(DUPLICATE_NAME.formatted(gameName));
    }
}
