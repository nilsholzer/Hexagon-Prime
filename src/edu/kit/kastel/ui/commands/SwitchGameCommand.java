package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.exceptions.BasicCommandException;
import edu.kit.kastel.model.logic.Hex;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.HexCommand;
import edu.kit.kastel.ui.Result;
import edu.kit.kastel.ui.ResultType;

/**
 * This command switches to another gameboard in Hex.
 * @author uhquw
 * @version 1.0.0
 */
public class SwitchGameCommand extends HexCommand {
    protected static final String COMMAND_NAME = "switch-game";
    protected static final String COMMAND_DESCRIPTION = "Switches current game to another game";
    private static final int COMMAND_ARGUMENTS = 1;

    /**
     * Constructs a new SwitchGameCommand.
     * @param commandHandler the command handler
     * @param hex            the hex game
     */
    public SwitchGameCommand(CommandHandler commandHandler, Hex hex) {
        super(COMMAND_NAME, commandHandler, hex, COMMAND_ARGUMENTS);
    }

    @Override
    protected Result executeHexCommand(String[] commandArguments) {
        try {
            return new Result(ResultType.SUCCESS, getHex().switchGame(commandArguments[0]));
        } catch (BasicCommandException exception) {
            return new Result(ResultType.FAILURE, exception.getMessage());
        }
    }
}
