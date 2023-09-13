package edu.kit.kastel.ui;

import java.util.Objects;

/**
 * This class represents a basic command that can be executed by the user.
 * @author Programmieren-Team
 * @version 1.0.1
 */
public abstract class Command {
    private final CommandHandler commandHandler;
    private final String commandName;

    /**
     * Constructs a new Command.
     * @param commandName    the name of the command
     * @param commandHandler the command handler
     */
    protected Command(final String commandName, final CommandHandler commandHandler) {
        this.commandHandler = Objects.requireNonNull(commandHandler);
        this.commandName = Objects.requireNonNull(commandName);
    }

    /**
     * Returns the name of the command.
     * @return the name of the command
     */
    public final String getCommandName() {
        return commandName;
    }

    /**
     * Returns the command handler of the command.
     * @return the command handler
     */
    protected CommandHandler getCommandHandler() {
        return commandHandler;
    }

    /**
     * Executes the command with the given arguments.
     * @param commandArguments the arguments of the command
     */
    public abstract void execute(String[] commandArguments);
}
