package edu.kit.kastel.model.exceptions;

public class SwitchGamesException extends IllegalArgumentException {
    private static final String INVALID_NAME_MESSAGE = "A game with that name does not exist";

    public SwitchGamesException() {
        super(INVALID_NAME_MESSAGE);
    }
}
