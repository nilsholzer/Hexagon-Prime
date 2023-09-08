package edu.kit.kastel.model.logic;

import edu.kit.kastel.model.entity.Hexagon;
import edu.kit.kastel.model.entity.Player;
import edu.kit.kastel.model.entity.Vector2D;
import edu.kit.kastel.model.exceptions.NewGameException;
import edu.kit.kastel.model.exceptions.SwitchGamesException;
import edu.kit.kastel.ui.ResultType;
import edu.kit.kastel.ui.commands.PlaceCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * This class describes the Hex game.
 * @author uhquw
 * @version 1.0.1
 */
public class Hex implements HexCommands {
    private static final String LIST_GAMES_FORMAT = "%s: %d";
    private static final String WELCOME_FORMAT = "Welcome to %s";
    private static final String FIRST_NAME = "Prime";
    private static final String SWITCHED_GAME_FORMAT = "Switched to %s";
    private static final String AUTO_PRINT = "auto-print";
    private final int size;
    private final String player1;
    private final String player2;
    private final boolean isAIGame;
    private final boolean autoPrint;
    private final List<Game> games;
    private Game currentGame;

    /**
     * Constructs a new Hex game, without the third parameter, so there is no autoprint.
     * @param size      the width and length of the game board
     * @param player1   the name of player 1
     * @param player2   the name of player 2
     * @param isAIGame  boolean, representing if the game is played against an AI
     */
    public Hex(final int size, final String player1, final String player2, final boolean isAIGame) {
        this(size, player1, player2, "", isAIGame);
    }

    /**
     * Constructs a new Hex game.
     * @param size      the width and length of the game board
     * @param player1   the name of player 1
     * @param player2   the name of player 2
     * @param autoPrint the third command argument, describing if the Hex game has autoprint
     * @param isAIGame  boolean, representing if the game is played against an AI
     */
    public Hex(final int size, final String player1, final String player2, final String autoPrint, final boolean isAIGame) {
        this.size = size;
        this.player1 = player1;
        this.player2 = player2;
        this.autoPrint = autoPrint.equals(AUTO_PRINT);
        this.isAIGame = isAIGame;

        games = new ArrayList<>();
        games.add(createGame(FIRST_NAME));
        currentGame = games.get(0);

        ResultType.SUCCESS.printResult(WELCOME_FORMAT, autoPrintWelcome(FIRST_NAME));
    }

    /**
     * Creates a game with the given name.
     * @param name the name of the game
     * @return A new AIGame, if the Hex game is played against an AI or else a PlayersGame
     */
    private Game createGame(final String name) {
        if (isAIGame) {
            return new AIGame(name, size, createPlayer1(), createPlayer2(), autoPrint);
        } else {
            return new PlayersGame(name, size, createPlayer1(), createPlayer2(), autoPrint);
        }
    }

    @Override
    public String history(final int turns) {
        return currentGame.history(turns);
    }

    @Override
    public String place(final Vector2D coordinates) {
        int xPos = coordinates.getxPos();
        int yPos = coordinates.getyPos();
        //Throws IllegalArgumentException, if the given coordinates are out of bounds.
        if (xPos >= size || yPos >= size) {
            throw new IllegalArgumentException(PlaceCommand.COORDINATE_INVALID_FORMAT.formatted(xPos, yPos));
        }
        return currentGame.place(coordinates);
    }
    @Override
    public String print() {
        return currentGame.print();
    }
    @Override
    public String swap() {
        return currentGame.swap();
    }

    /**
     * Lists all active games in the games list.
     * @return A visualization of all the games´ names and the amount of their turns
     */
    public String listGames() {
        StringBuilder result = new StringBuilder();
        ListIterator<Game> gamesIterator = games.listIterator();
        while (gamesIterator.hasNext()) {
            Game game = gamesIterator.next();
            if (!game.isActive()) {
                continue;
            }
            result.append(LIST_GAMES_FORMAT.formatted(game.getName(), game.getTurnsSize()));
            if (gamesIterator.hasNext()) {
                result.append(ResultType.NEW_LINE_SYMBOL);
            }
        }
        return result.toString();
    }

    /**
     * Creates a new game and switches the current game to it.
     * @param name the name of the new Game
     * @return A confirmation of the successful creation of a new game
     * @throws NewGameException when a game with this name already exists
     */
    public String newGame(final String name) {
        for (Game game : games) {
            if (name.equals(game.getName())) {
                throw new NewGameException(name);
            }
        }
        games.add(createGame(name));
        currentGame = games.get(games.size() - 1);
        return WELCOME_FORMAT.formatted(autoPrintWelcome(name));
    }

    /**
     * Switches the current game to another game.
     * @param switchedGameName the name of the game to be switched to
     * @return A confirmation of the successful switch to another game
     * @throws SwitchGamesException when a game with this name does not exist
     */
    public String switchGame(final String switchedGameName) {
        if (switchedGameName.equals(currentGame.getName())
                || games.stream().noneMatch(game -> game.getName().equals(switchedGameName))) {
            throw new SwitchGamesException();
        }
        for (Game switchedGame : games) {
            if (switchedGameName.equals(switchedGame.getName())) {
                currentGame = switchedGame;
                break;
            }
        }
        return SWITCHED_GAME_FORMAT.formatted(switchedGameName);
    }
    //Prints a welcome message, if a new game is started.
    private String autoPrintWelcome(final String gameName) {
        return gameName + System.lineSeparator() +  currentGame.update();
    }
    //creates Player 1
    private Player createPlayer1() {
        return new Player(player1, Hexagon.RED);
    }
    //creates Player 2
    private Player createPlayer2() {
        return new Player(player2, Hexagon.BLUE);
    }
}
