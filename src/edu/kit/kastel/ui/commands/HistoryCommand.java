package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.exceptions.BasicCommandException;
import edu.kit.kastel.model.logic.GameBuilder;
import edu.kit.kastel.model.logic.Hex;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.HexCommand;
import edu.kit.kastel.ui.Result;
import edu.kit.kastel.ui.ResultType;

import java.util.regex.Matcher;

/**
 * This command shows the history of turns of the current game of hex.
 * @author uhquw
 * @version 1.0.0
 */
public class HistoryCommand extends HexCommand {
    protected static final String COMMAND_NAME = "history";
    protected static final String COMMAND_DESCRIPTION = "Lists coordinates of the last placed tokens";
    private static final int EXPECTED_ARGUMENTS = 0;
    private static final String ARGUMENT_NOT_A_NUMBER = "Given argument is not a number";

    /**
     * Constructs a new HistoryCommand.
     * @param commandHandler the command handler
     * @param hex            the hex game
     */
    public HistoryCommand(CommandHandler commandHandler, Hex hex) {
        super(COMMAND_NAME, commandHandler, hex, EXPECTED_ARGUMENTS);
    }

    @Override
    protected Result executeHexCommand(String[] commandArguments) {
        int turns = 1;
        if (commandArguments.length == 1) {
            Matcher matcher = GameBuilder.getNumberPattern().matcher(commandArguments[0]);
            if (!matcher.matches()) {
                return new Result(ResultType.FAILURE, ARGUMENT_NOT_A_NUMBER);
            }
            turns = Integer.parseInt(commandArguments[0]);
            if (turns == 0) {
                return new Result(ResultType.FAILURE, ARGUMENT_NOT_A_NUMBER);
            }
        }
        try {
            return new Result(ResultType.SUCCESS, getHex().history(turns));
        } catch (BasicCommandException exception) {
            return new Result(ResultType.FAILURE, exception.getMessage());
        }
    }
    @Override
    protected boolean optionalArguments() {
        return true;
    }
}
