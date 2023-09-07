package edu.kit.kastel.model.logic;

import edu.kit.kastel.model.entity.AIType;
import edu.kit.kastel.model.entity.Hexagon;
import edu.kit.kastel.model.entity.Player;
import edu.kit.kastel.model.entity.Vector2D;
import edu.kit.kastel.model.exceptions.PlaceException;
import edu.kit.kastel.ui.ResultType;

/**
 * This class describes one game of Hex played against an AI.
 * @author uhquw
 * @version 1.0.1
 */
public class AIGame extends Game {
    private final AIType aiType;
    /**
     * Constructs a new game of Hex played against an AI.
     * @param name      the name of the game
     * @param gameboardSize      the size of the game board
     * @param player1   the first player, who is a real person
     * @param player2   the second player, who is an AI
     * @param autoPrint describes, if autoprint is enabled for the game
     */
    public AIGame(final String name, final int gameboardSize, final Player player1, final Player player2, final boolean autoPrint) {
        super(name, gameboardSize, player1, player2, autoPrint);
        aiType = AIType.valueOf(player2.getName());
    }

    @Override
    public String place(Vector2D coordinates) {
        if (!isActive()) {
            throw new PlaceException();
        }
        Player currentPlayer = getCurrentPlayer();
        Hexagon playersToken = currentPlayer.getToken();
        GameBoard gameBoard = getGameBoard();
        gameBoard.place(coordinates, playersToken);
        addTurn(coordinates, currentPlayer);
        String result = update();
        //AIPlayer aiPlayer = (AIPlayer) getCurrentPlayer();
        result += ResultType.NEW_LINE_SYMBOL + aiPlace(playersToken, gameBoard);
        //result += ResultType.NEW_LINE_SYMBOL + update();
        return result;
    }
    private String aiPlace(Hexagon playersToken, GameBoard gameBoard) {
        Hexagon aiToken = getCurrentPlayer().getToken();
        Vector2D aiWinningvector = gameBoard.winInNextMove(aiToken);
        if (aiWinningvector != null) {
            gameBoard.place(aiWinningvector, aiToken);
            return update();
        }
        Vector2D playerWinningVector = gameBoard.winInNextMove(playersToken);
        if (playerWinningVector != null) {
            gameBoard.place(playerWinningVector, aiToken);
            return update();
        } else {
            return aiType.turn(this);
        }
    }
}
