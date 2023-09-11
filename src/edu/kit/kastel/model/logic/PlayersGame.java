package edu.kit.kastel.model.logic;

import edu.kit.kastel.model.entity.Player;
import edu.kit.kastel.model.entity.Vector2D;
import edu.kit.kastel.model.exceptions.PlaceException;

/**
 * This class describes one game of Hex played by two players.
 * @author uhquw
 * @version 1.0.1
 */
public class PlayersGame extends Game {
    /**
     * Constructs a new game of Hex played by two players.
     * @param name      the name of the game
     * @param gameboardSize      the size of the game board
     * @param player1   the first player, who is a real person
     * @param player2   the second player, who is also a real person
     * @param autoPrint describes, if autoprint is enabled for the game
     */
    public PlayersGame(final String name, final int gameboardSize, final Player player1, final Player player2, final boolean autoPrint) {
        super(name, gameboardSize, player1, player2, autoPrint);
    }

    @Override
    public String place(Vector2D coordinates) {
        if (!isActive()) {
            throw new PlaceException(CANNOT_PLACE_AFTER_WIN);
        }
        Player currentPlayer = getCurrentPlayer();
        GameBoard gameBoard = getGameBoard();
        gameBoard.place(coordinates, currentPlayer.getToken());
        addTurn(coordinates, currentPlayer);
        return update();
    }
}
