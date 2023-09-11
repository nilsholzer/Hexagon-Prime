package edu.kit.kastel.model.logic;

import edu.kit.kastel.model.entity.Player;
import edu.kit.kastel.model.entity.Vector2D;
import edu.kit.kastel.model.exceptions.BasicCommandException;
import edu.kit.kastel.ui.ResultType;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * This class describes one Game of Hex.
 * @author uhquw
 * @version 1.0.2
 */
public abstract class Game implements Executable, HexCommands {
    protected static final String CANNOT_PLACE_AFTER_WIN = "You cannot place another hexagon, if a player has already won";
    private static final String SWAP_ERROR_MESSAGE = "You cannot swap players this turn";
    private static final String INVALID_TURN = "You cannot look at the last %s moves if the game only had %s moves";
    private static final String HISTORY_FORMAT = "%s: %d %d";
    private static final String SWAP_FORMAT = "%s swaps";
    private static final String TURN_FORMAT = "%s's turn";
    private static final String WINNER_FORMAT = "%s wins!";
    private Player currentPlayer;
    private final GameBoard gameBoard;
    private final String name;
    private final Player player1;
    private final Player player2;
    private ExecutionState executionState;
    private final boolean autoPrint;
    // A list containing every turn of the game, consisting the player and the coordinates of the turn.
    private final List<Entry<Vector2D, Player>> turns;
    private boolean swapped;

    /**
     * Constructs a new game of Hex.
     * @param name      the unique name of the game
     * @param gameboardSize      the size of the game board
     * @param player1   the first player, who needs to be a real person
     * @param player2   the second player(AI or real person)
     * @param autoPrint describes, if autoprint is enabled for the game
     */
    protected Game(final String name, final int gameboardSize, final Player player1, final Player player2, final boolean autoPrint) {
        this.name = Objects.requireNonNull(name);
        this.autoPrint = autoPrint;
        gameBoard = new GameBoard(gameboardSize);
        this.player1 = player1;
        this.player2 = player2;
        this.turns = new ArrayList<>();
        executionState = ExecutionState.RUNNING;
        swapped = false;
    }

    @Override
    public boolean isActive() {
        return executionState == ExecutionState.RUNNING;
    }

    @Override
    public void quit() {
        executionState = ExecutionState.ClOSED;
    }

    @Override
    public String history(final int turns) {
        if (turns > getTurnsSize()) {
            throw new BasicCommandException(INVALID_TURN.formatted(turns, getTurnsSize()));
        }
        StringBuilder result = new StringBuilder();
        // A list containing only the last 'turns' turns.
        List<Entry<Vector2D, Player>> outputList = this.turns.subList(getTurnsSize() - turns, getTurnsSize());
        ListIterator<Entry<Vector2D, Player>> listIterator = outputList.listIterator(outputList.size());
        //Iterates through the list from the back.
        while (listIterator.hasPrevious()) {
            Entry<Vector2D, Player> entry = listIterator.previous();
            String name = entry.getValue().getName();
            int xPos = entry.getKey().getxPos();
            int yPos = entry.getKey().getyPos();
            result.append(HISTORY_FORMAT.formatted(name, xPos, yPos));
            if (listIterator.hasPrevious()) {
                result.append(ResultType.NEW_LINE_SYMBOL);
            }
        }
        return result.toString();
    }

    @Override
    public String print() {
        return gameBoard.print();
    }

    @Override
    public String swap() {
        if (getTurnsSize() !=  1 || swapped) {
            throw new BasicCommandException(SWAP_ERROR_MESSAGE);
        }
        swapped = true;
        player1.swap();
        player2.swap();
        Vector2D firstCoordinates = turns.get(0).getKey();
        turns.set(0, new SimpleEntry<>(firstCoordinates, player2));
        return SWAP_FORMAT.formatted(player2.getName()) + ResultType.NEW_LINE_SYMBOL + update();
    }

    /**
     * Gets the size of the turns array.
     * @return the size of the turns array.
     */
    public int getTurnsSize() {
        return turns.size();
    }

    /**
     * Gets the name of the game.
     * @return the name of the game
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the game board of the game.
     * @return the game board of the game
     */
    public GameBoard getGameBoard() {
        return this.gameBoard;
    }

    /**
     * Updates the players of the game and checks if there is a winner.
     * Also it prints the game board, if autoprint is enabled.
     * @return The winner and the winning path, if there is a winner
     *         The player, whose turn is next
     *         The game board, if autoprint is enabled
     */
    public String update() {
        if (currentPlayer != null && gameBoard.isWinner(currentPlayer.getToken())) {
            quit();
            return WINNER_FORMAT.formatted(currentPlayer.getName()) + ResultType.NEW_LINE_SYMBOL + gameBoard.printWinner();
        }
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
        StringBuilder result = new StringBuilder();
        if (autoPrint) {
            result.append(print()).append(System.lineSeparator());
        }
        result.append(TURN_FORMAT.formatted(currentPlayer.getName()));
        return result.toString();
    }

    /**
     * Gets the 'moveCounts' last move of a game.
     * @param moveCount the move to be looked at
     * @return the last move of a game
     */
    public Vector2D getMove(final int moveCount) {
        return turns.get(getTurnsSize() - moveCount).getKey();
    }

    /**
     * Gets the current player, whose turn is next.
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Adds a new turn to the turns list.
     * @param coordinates the coordinates of the turn
     * @param player      the player who placed the token
     */
    public void addTurn(final Vector2D coordinates, final Player player) {
        turns.add(new SimpleEntry<>(coordinates, player));
    }

    @Override
    public abstract String place(Vector2D coordinates);
}
