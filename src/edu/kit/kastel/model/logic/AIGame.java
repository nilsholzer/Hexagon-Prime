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
 * @version 1.0.4
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
            throw new PlaceException(CANNOT_PLACE_AFTER_WIN);
        }
        Player currentPlayer = getCurrentPlayer();
        Hexagon playersToken = currentPlayer.getToken();
        GameBoard gameBoard = getGameBoard();
        String result = placeHexagon(coordinates, currentPlayer, gameBoard);
        if (isActive()) {
            result += ResultType.NEW_LINE_SYMBOL + aiPlace(playersToken, gameBoard);
        }
        return result;
    }

    /**
     * Places a token for the AI, depending if the AI can win on next move or the player can win on next move.
     * If neither can, it will place a stone or swap players depending on the AI´s implementation
     * @param playersToken the token of the real player
     * @param gameBoard    the game board, where the AI places its stone
     * @return A confirmation of the successful placement of the stone, or the successful swap
     */
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

    /**
     * Gets the point symmetric hexagon of the given hexagon coordinates.
     * @param xPos the xPos of the hexagon
     * @param yPos the yPos of the hexagon
     * @return the point symmetric hexagon coordinates
     */
    public Vector2D getPointSymmetric(final int xPos, final int yPos) {
        GameBoard gameBoard = getGameBoard();
        int middle = (gameBoard.getSize() - 1) / 2;
        int distXMiddle = middle - xPos;
        int distYMiddle = middle - yPos;
        return new Vector2D(middle + distXMiddle, middle + distYMiddle);
    }

    /**
     * Places a stone on the given coordinates on the gameboard, for the given player and updates the game after.
     * @param coordinates   the coordinates of the placed token
     * @param currentPlayer the player, who places the token
     * @param gameBoard     the game board, where the token is placed on
     * @return An update, that it´s the next players turn or a winnning confirmation
     */
    private String placeHexagon(Vector2D coordinates, Player currentPlayer, GameBoard gameBoard) {
        gameBoard.place(coordinates, currentPlayer.getToken());
        addTurn(coordinates, currentPlayer);
        return update();
    }
}
