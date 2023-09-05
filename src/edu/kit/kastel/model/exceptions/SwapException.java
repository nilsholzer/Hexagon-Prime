package edu.kit.kastel.model.exceptions;

public class SwapException extends IllegalArgumentException {
    private static final String SWAP_ERROR_MESSAGE = "You cannot swap players this turn";

    public SwapException() {
        super(SWAP_ERROR_MESSAGE);
    }
}
