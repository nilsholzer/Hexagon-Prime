package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.logic.Hex;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.HexCommand;
import edu.kit.kastel.ui.Result;
import edu.kit.kastel.ui.ResultType;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * This command lists the user all the possible commands.
 *
 * @author uhquw
 * @version 1.0.0
 */
public class HelpCommand extends HexCommand {
    private static final String COMMAND_NAME = "help";
    private static final int EXPECTED_ARGUMENTS = 0;
    private static final String COMMAND_DESCRIPTION = "Prints this help message";
    private static final String COMMAND_OUTPUT = "* %s: %s";
    private final Map<String, String> commands;

    /**
     * Constructs a new help command.
     * @param commandHandler the command handler, where the command is being executed
     * @param hex            the game where the command is being executed
     */
    public HelpCommand(final CommandHandler commandHandler, final Hex hex) {
        super(COMMAND_NAME, commandHandler, hex, EXPECTED_ARGUMENTS);
        commands = new TreeMap<>();
        this.initCommands();
    }

    @Override
    protected Result executeHexCommand(final String[] commandArguments) {
        return new Result(ResultType.SUCCESS, this.printCommands());
    }

    private void initCommands() {
        this.commands.put(COMMAND_NAME, COMMAND_DESCRIPTION);
        this.commands.put(HistoryCommand.COMMAND_NAME, HistoryCommand.COMMAND_DESCRIPTION);
        this.commands.put(ListGamesCommand.COMMAND_NAME, ListGamesCommand.COMMAND_DESCRIPTION);
        this.commands.put(NewGameCommand.COMMAND_NAME, NewGameCommand.COMMAND_DESCRIPTION);
        this.commands.put(PlaceCommand.COMMAND_NAME, PlaceCommand.COMMAND_DESCRIPTION);
        this.commands.put(PrintCommand.COMMAND_NAME, PrintCommand.COMMAND_DESCRIPTION);
        this.commands.put(QuitCommand.COMMAND_NAME, QuitCommand.COMMAND_DESCRIPTION);
        this.commands.put(SwapCommand.COMMAND_NAME, SwapCommand.COMMAND_DESCRIPTION);
        this.commands.put(SwitchGameCommand.COMMAND_NAME, SwitchGameCommand.COMMAND_DESCRIPTION);
    }

    private String printCommands() {
        StringBuilder result = new StringBuilder();
        Iterator<Entry<String, String>> commandIterator = commands.entrySet().iterator();
        while (commandIterator.hasNext()) {
            Entry<String, String> command = commandIterator.next();
            result.append(COMMAND_OUTPUT.formatted(command.getKey(), command.getValue()));
            if (commandIterator.hasNext()) {
                result.append(ResultType.NEW_LINE_SYMBOL);
            }
        }
        return result.toString();
    }
}
