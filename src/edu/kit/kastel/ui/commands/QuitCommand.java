package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.logic.Hex;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.HexCommand;
import edu.kit.kastel.ui.Result;

/**
 * This command quits the game.
 *
 * @author Programmieren-Team
 * @version 1.0.0
 */
public class QuitCommand extends HexCommand {
    protected static final String COMMAND_NAME = "quit";
    protected static final String COMMAND_DESCRIPTION = "Quit all games and end program";
    private static final int EXPECTED_ARGUMENTS = 0;

    /**
     * Constructs a quit command.
     * @param commandHandler the command handler, where the command is being executed
     * @param hex            the game where the command is being executed
     */
    public QuitCommand(final CommandHandler commandHandler, final Hex hex) {
        super(COMMAND_NAME, commandHandler, hex, EXPECTED_ARGUMENTS);
    }

    @Override
    protected Result executeHexCommand(final String[] commandArguments) {
        getCommandHandler().quit();
        return null;
    }
}
