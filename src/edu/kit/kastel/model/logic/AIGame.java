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
 * @version 1.0.3
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
        String result = placeHexagon(coordinates, currentPlayer, gameBoard);
        if (!isActive()) {
            result += ResultType.NEW_LINE_SYMBOL + aiPlace(playersToken, gameBoard);
        }
        return result;
    }
    private String aiPlace(Hexagon playersToken, GameBoard gameBoard) {
        String aiName = aiType.getName();
        Player aiPlayer = getCurrentPlayer();
        Hexagon aiToken = aiPlayer.getToken();
        Vector2D aiWinningvector = gameBoard.winInNextMove(aiToken);
        StringBuilder result = new StringBuilder();
        int xPos;
        int yPos;
        if (aiWinningvector != null) {
            xPos = aiWinningvector.getxPos();
            yPos = aiWinningvector.getyPos();
            result.append(AIType.AI_PLACE_FORMAT.formatted(aiName, xPos, yPos))
                    .append(placeHexagon(aiWinningvector, aiPlayer, gameBoard));
            return result.toString();
        }
        Vector2D playerWinningVector = gameBoard.winInNextMove(playersToken);
        if (playerWinningVector != null) {
            xPos = playerWinningVector.getxPos();
            yPos = playerWinningVector.getyPos();
            result.append(AIType.AI_PLACE_FORMAT.formatted(aiName, xPos, yPos))
                    .append(placeHexagon(playerWinningVector, aiPlayer, gameBoard));
            return result.toString();
        } else {
            return aiType.turn(this);
        }
    }
    private String placeHexagon(Vector2D coordinates, Player currentPlayer, GameBoard gameBoard) {
        gameBoard.place(coordinates, currentPlayer.getToken());
        addTurn(coordinates, currentPlayer);
        return update();
    }
}
