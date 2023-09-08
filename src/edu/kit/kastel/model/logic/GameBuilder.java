package edu.kit.kastel.model.logic;

import edu.kit.kastel.model.exceptions.BuildException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class describes a Game builder, building a Game of Hex.
 * @author uhquw
 * @version 1.0.0
 */
public class GameBuilder {
    //Regex for a number.
    private static final String NUMBER_REGEX = "\\d+";
    private static final String AI_REGEX = "^(BogoAI|HeroAI)$";
    private static final int MINIMUM_ARGUMENTS = 3;
    private static final int MAXIMUM_ARGUMENTS = 4;
    private static final int MAXIMUM_SIZE = 12345;
    private static final int MINIMUM_SIZE = 5;
    private static final String ARGUMENT_SIZE_ERROR = "Not the expected amount of arguments";
    private static final String NOT_A_NUMBER_ERROR = "First argument is not a number";
    private static final String INVALID_SIZE_ERROR = "Invalid size of the gameboard";
    private static final String UNIQUE_NAMES_ERROR = "Players need to have unique names";
    private static final String PLAYER1_ERROR = "Player 1 cannot be named after an AI";
    //Pattern for the Number regex.
    private static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);
    //Pattern for the Ai regex.
    private static final Pattern AI_PATTERN = Pattern.compile(AI_REGEX);

    /**
     * Builds a game of Hex according to the arguments.
     * @param arguments       the arguments, which are essential for the creation of a game
     * @throws BuildException when the given arguments are wrong
     * @return                the explicit game of Hex
     */
    public Hex buildGame(String[] arguments) {
        if (arguments.length > MAXIMUM_ARGUMENTS || arguments.length < MINIMUM_ARGUMENTS) {
            throw new BuildException(ARGUMENT_SIZE_ERROR);
        }
        Matcher matcher = NUMBER_PATTERN.matcher(arguments[0]);
        if (!matcher.matches()) {
            throw new BuildException(NOT_A_NUMBER_ERROR);
        }
        int size = Integer.parseInt(arguments[0]);
        if (size > MAXIMUM_SIZE ||  size < MINIMUM_SIZE || size % 2 == 0) {
            throw new BuildException(INVALID_SIZE_ERROR);
        }
        if (arguments[1].equals(arguments[2])) {
            throw new BuildException(UNIQUE_NAMES_ERROR);
        }
        matcher = AI_PATTERN.matcher(arguments[1]);
        if (matcher.matches()) {
            throw new BuildException(PLAYER1_ERROR);
        }
        //Checks if the second players name matches an AIType, so the game knows if player 1 plays against an AI.
        matcher = AI_PATTERN.matcher(arguments[2]);
        if (arguments.length == MAXIMUM_ARGUMENTS) {
            //Returns a new Hex game, possibly with the autoprint feature, if the String is correct.
            return new Hex(size, arguments[1], arguments[2], arguments[MINIMUM_ARGUMENTS], matcher.matches());
        } else {
            //Returns a new Hex game without the autoprint feature.
            return new Hex(size, arguments[1], arguments[2], matcher.matches());
        }
    }

    /**
     * Gets the regex pattern of a number.
     * @return the regex pattern of a number
     */
    public static Pattern getNumberPattern() {
        return NUMBER_PATTERN;
    }
}
