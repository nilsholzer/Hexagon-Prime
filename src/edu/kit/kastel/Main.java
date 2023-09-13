package edu.kit.kastel;

import edu.kit.kastel.model.exceptions.BuildException;
import edu.kit.kastel.model.logic.GameBuilder;
import edu.kit.kastel.model.logic.Hex;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.ResultType;

/**
 * Main entry class to run application.
 * @author uhquw
 * @version 1.0.0
 */
public final class Main {

    private Main() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

    /**
     * Main method used as entry point.
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        Hex game;
        try {
            GameBuilder gameBuilder = new GameBuilder();
            game = gameBuilder.buildGame(args);
        } catch (BuildException exception) {
            ResultType.FAILURE.printResult(exception.getMessage());
            game = null;
        }
        if (game == null) {
            return;
        }
        CommandHandler session = new CommandHandler(game);
        session.handleUserInput();
    }
}
