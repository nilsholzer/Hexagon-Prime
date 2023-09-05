package edu.kit.kastel.model.logic;

import edu.kit.kastel.model.entity.Person;
import edu.kit.kastel.model.entity.Player;
import edu.kit.kastel.model.entity.Vector2D;

/**
 * This class describes one game of Hex played by two players.
 * @author uhquw
 * @version 1.0.0
 */
public class PersonGame extends Game {
    /**
     * Constructs a new game of Hex played by two players.
     * @param name      the name of the game
     * @param size      the size of the game board
     * @param player1   the first player, who is a real person
     * @param player2   the second player, who is also a real person
     * @param autoPrint describes, if autoprint is enabled for the game
     */
    public PersonGame(final String name, final int size, final Person player1, final Player player2, final boolean autoPrint) {
        super(name, size, player1, player2, autoPrint);
    }

    @Override
    public String place(Vector2D coordinates) {
        Player currentPlayer = getCurrentPlayer();
        GameBoard gameBoard = getGameBoard();
        gameBoard.place(coordinates, currentPlayer.getToken());
        addTurn(coordinates, currentPlayer);
        return update();
    }
}
