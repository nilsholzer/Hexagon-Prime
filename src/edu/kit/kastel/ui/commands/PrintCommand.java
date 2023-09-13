package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.logic.Hex;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.HexCommand;
import edu.kit.kastel.ui.Result;
import edu.kit.kastel.ui.ResultType;

/**
 * This command prints the game board of the current game of hex.
 * @author uhquw
 * @version 1.0.0
 */
public class PrintCommand extends HexCommand {
    protected static final String COMMAND_NAME = "print";
    protected static final String COMMAND_DESCRIPTION = "Prints the current game board";
    private static final int COMMAND_ARUMENTS = 0;

    /**
     * Constructs a new PrintCommand.
     * @param commandHandler the command handler
     * @param hex            the hex game
     */
    public PrintCommand(CommandHandler commandHandler, Hex hex) {
        super(COMMAND_NAME, commandHandler, hex, COMMAND_ARUMENTS);
    }

    @Override
    protected Result executeHexCommand(String[] commandArguments) {
        return new Result(ResultType.SUCCESS, getHex().print());
    }
}
