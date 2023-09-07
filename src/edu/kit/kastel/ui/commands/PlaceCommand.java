package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.entity.Vector2D;
import edu.kit.kastel.model.logic.GameBuilder;
import edu.kit.kastel.model.logic.Hex;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.HexCommand;
import edu.kit.kastel.ui.Result;
import edu.kit.kastel.ui.ResultType;

import java.util.regex.Matcher;

/**
 * This command places a token on the game board.
 * @author uhquw
 * @version 1.0.0
 */
public class PlaceCommand extends HexCommand {
    /**
     * Error message that occurs, when the given coordinate is out of bounds.
     */
    public static final String COORDINATE_INVALID_FORMAT = "the coordinate (%d, %d) is not valid.";
    protected static final String COMMAND_NAME = "place";
    protected static final String COMMAND_DESCRIPTION = "Places a new token on the game board";
    private static final String INVALID_ARGUMENTS = "The given arguments do not contain two numbers";
    private static final int COMMAND_ARGUMENTS = 2;

    /**
     * Constructs a new PlaceCommand.
     * @param commandHandler the command handler
     * @param hex            the hex game
     */
    public PlaceCommand(CommandHandler commandHandler, Hex hex) {
        super(COMMAND_NAME, commandHandler, hex, COMMAND_ARGUMENTS);
    }

    @Override
    protected Result executeHexCommand(String[] commandArguments) {
        Matcher matcher1 = GameBuilder.getNumberPattern().matcher(commandArguments[0]);
        Matcher matcher2 = GameBuilder.getNumberPattern().matcher(commandArguments[1]);
        if (!matcher1.matches() || !matcher2.matches()) {
            return new Result(ResultType.FAILURE, INVALID_ARGUMENTS);
        }
        int xPos = Integer.parseInt(commandArguments[0]);
        int yPos = Integer.parseInt(commandArguments[1]);
        if (xPos < 0 || yPos < 0) {
            return new Result(ResultType.FAILURE, COORDINATE_INVALID_FORMAT.formatted(xPos, yPos));
        }
        Vector2D coordinates = new Vector2D(xPos, yPos);
        try {
            return new Result(ResultType.SUCCESS, hex.place(coordinates));
        } catch (IllegalArgumentException exception) {
            return new Result(ResultType.FAILURE, exception.getMessage());
        }
    }
}
