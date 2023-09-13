package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.logic.Hex;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.HexCommand;
import edu.kit.kastel.ui.Result;
import edu.kit.kastel.ui.ResultType;

/**
 * This command lists all the running games of hex.
 * @author uhquw
 * @version 1.0.0
 */
public class ListGamesCommand extends HexCommand {
    protected static final String COMMAND_NAME = "list-games";
    protected static final String COMMAND_DESCRIPTION = "Lists all current running games";
    private static final int COMMAND_ARGUMENTS = 0;

    /**
     * Constructs a new ListGamesCommand.
     * @param commandHandler the command handler
     * @param hex            the hex game
     */
    public ListGamesCommand(final CommandHandler commandHandler, final Hex hex) {
        super(COMMAND_NAME, commandHandler, hex, COMMAND_ARGUMENTS);
    }

    @Override
    protected Result executeHexCommand(final String[] commandArguments) {
        return new Result(ResultType.SUCCESS, getHex().listGames());
    }
}
