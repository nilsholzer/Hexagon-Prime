package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.exceptions.BasicCommandException;
import edu.kit.kastel.model.logic.Hex;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.HexCommand;
import edu.kit.kastel.ui.Result;
import edu.kit.kastel.ui.ResultType;

/**
 * This command creates a new game of hex.
 * @author uhquw
 * @version 1.0.0
 */
public class NewGameCommand extends HexCommand {
    protected static final String COMMAND_NAME = "new-game";
    protected static final String COMMAND_DESCRIPTION = "Starts a new game";
    private static final int COMMAND_ARGUMENTS = 1;

    /**
     * Constructs a new NewGameCommand.
     * @param commandHandler the command handler
     * @param hex            the hex game
     */
    public NewGameCommand(CommandHandler commandHandler, Hex hex) {
        super(COMMAND_NAME, commandHandler, hex, COMMAND_ARGUMENTS);
    }

    @Override
    protected Result executeHexCommand(String[] commandArguments) {
        try {
            return new Result(ResultType.SUCCESS, getHex().newGame(commandArguments[0]));
        } catch (BasicCommandException exception) {
            return new Result(ResultType.FAILURE, exception.getMessage());
        }
    }
}
