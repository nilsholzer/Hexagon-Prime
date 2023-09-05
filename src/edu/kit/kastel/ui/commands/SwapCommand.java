package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.exceptions.SwapException;
import edu.kit.kastel.model.logic.Hex;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.HexCommand;
import edu.kit.kastel.ui.Result;
import edu.kit.kastel.ui.ResultType;

/**
 * This command swaps players in a game of hex.
 * @author uhquw
 * @version 1.0.0
 */
public class SwapCommand extends HexCommand {
    protected static final String COMMAND_NAME = "swap";
    protected static final String COMMAND_DESCRIPTION = "Allows player 2 to switch colors";
    private static final int COMMMAND_ARGUMENTS = 0;

    /**
     * Constructs a new SwapCommand.
     * @param commandHandler the command handler
     * @param hex            the hex game
     */
    public SwapCommand(CommandHandler commandHandler, Hex hex) {
        super(COMMAND_NAME, commandHandler, hex, COMMMAND_ARGUMENTS);
    }

    @Override
    protected Result executeHexCommand(String[] commandArguments) {
        try {
            return new Result(ResultType.SUCCESS, hex.swap());
        } catch (SwapException exception) {
            return new Result(ResultType.FAILURE, exception.getMessage());
        }
    }
}
