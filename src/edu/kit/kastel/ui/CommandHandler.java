package edu.kit.kastel.ui;

import edu.kit.kastel.model.logic.Hex;
import edu.kit.kastel.ui.commands.HelpCommand;
import edu.kit.kastel.ui.commands.HistoryCommand;
import edu.kit.kastel.ui.commands.ListGamesCommand;
import edu.kit.kastel.ui.commands.NewGameCommand;
import edu.kit.kastel.ui.commands.PlaceCommand;
import edu.kit.kastel.ui.commands.PrintCommand;
import edu.kit.kastel.ui.commands.QuitCommand;
import edu.kit.kastel.ui.commands.SwapCommand;
import edu.kit.kastel.ui.commands.SwitchGameCommand;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 * This class handles the user input and executes the commands.
 *
 * @author Programmieren-Team
 * @version 1.0.0
 */
public final class CommandHandler {
    private static final String COMMAND_SEPARATOR_REGEX = "\\s+";
    private static final String COMMAND_NOT_FOUND = "Error: Command '%s' not found";
    private final Hex hex;
    private final Map<String, Command> commands;
    private boolean running = false;

    /**
     * Constructs a new CommandHandler.
      * @param hex the hex game that this instance manages
     */
    public CommandHandler(Hex hex) {
        this.hex = Objects.requireNonNull(hex);
        this.commands = new HashMap<>();
        this.initCommands();
    }

    /**
     * Starts the interaction with the user.
     */
    public void handleUserInput() {
        this.running = true;

        try (Scanner scanner = new Scanner(System.in)) {
            while (running && scanner.hasNextLine()) {
                executeCommand(scanner.nextLine());
            }
        }
    }

    /**
     * Quits the interaction with the user.
     */
    public void quit() {
        this.running = false;
    }

    /**
     * Executes the command according to the users input.
     * @param commandWithArguments The input of the user
     */
    private void executeCommand(String commandWithArguments) {
        String[] splittedCommand = commandWithArguments.trim().split(COMMAND_SEPARATOR_REGEX);
        String commandName = splittedCommand[0];
        String[] commandArguments = Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length);

        if (commands.containsKey(commandName)) {
            commands.get(commandName).execute(commandArguments);
        } else {
            ResultType.FAILURE.printResult(COMMAND_NOT_FOUND, commandName);
        }
    }

    /**
     * Initializes all the commands that can be used.
     */
    private void initCommands() {
        this.addCommand(new HelpCommand(this, hex));
        this.addCommand(new HistoryCommand(this, hex));
        this.addCommand(new ListGamesCommand(this, hex));
        this.addCommand(new NewGameCommand(this, hex));
        this.addCommand(new PlaceCommand(this, hex));
        this.addCommand(new PrintCommand(this, hex));
        this.addCommand(new QuitCommand(this, hex));
        this.addCommand(new SwapCommand(this, hex));
        this.addCommand(new SwitchGameCommand(this, hex));
    }

    /**
     * Adds a command to the command Map.
     * @param commmand the command being added
     */
    private void addCommand(Command commmand) {
        this.commands.put(commmand.getCommandName(), commmand);
    }
}
