package edu.kit.kastel.ui;

import edu.kit.kastel.model.logic.Hex;

import java.util.Objects;

/**
 * This class represents a command for the Hex game.
 * @author Programmieren-Team
 * @version 1.0.0
 */
public abstract class HexCommand extends Command {
    /**
     * Standard output for a winner.
     */
    protected static final String WINNER_FORMAT = "%s wins!";
    private static final String NOT_EXPECTED_ARGS_LENGTH_ERROR = "Expected %d arguments but got %d";
    private static final String OPTIONAL_ARGS_LENGTH_ERROR = "Expected %d or less arguments but got %d";
    /**
     * The hex game.
     */
    protected final Hex hex;
    private final int expectedNumberOfArguments;

    /**
     * Constructs a new HexCommand.
     * @param commandName               the name of the command
     * @param commandHandler            the command handler
     * @param hex                       the hex game
     * @param expectedNumberOfArguments the expected number of arguments
     */
    protected HexCommand(String commandName, CommandHandler commandHandler, Hex hex, int expectedNumberOfArguments) {
        super(commandName, commandHandler);
        this.hex = Objects.requireNonNull(hex);
        this.expectedNumberOfArguments = expectedNumberOfArguments;
    }

    @Override
    public final void execute(String[] commandArguments) {
        if (commandArguments.length != expectedNumberOfArguments && !optionalArguments()) {
            ResultType.FAILURE.printResult(NOT_EXPECTED_ARGS_LENGTH_ERROR, expectedNumberOfArguments,
                    commandArguments.length);
        } else if (optionalArguments() && commandArguments.length > expectedNumberOfArguments + 1) {
            ResultType.FAILURE.printResult(OPTIONAL_ARGS_LENGTH_ERROR, expectedNumberOfArguments + 1,
                    commandArguments.length);
        } else {
            Result result = executeHexCommand(commandArguments);
            if (result != null) {
                result.getType().printResult(result.getMessage());
            }
        }
    }

    /**
     * Returns if the command has optional arguments.
     * @return Boolean, if the command has optional arguments
     */
    protected boolean optionalArguments() {
        return false;
    }

    /**
     * Executes the command
     * @param commandArguments the command arguments.
     * @return                 the result of the command
     */
    protected abstract Result executeHexCommand(String[] commandArguments);
}
