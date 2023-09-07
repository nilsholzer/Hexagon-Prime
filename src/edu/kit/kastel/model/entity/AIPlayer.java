package edu.kit.kastel.model.entity;

import edu.kit.kastel.model.logic.AIGame;
import edu.kit.kastel.model.logic.GameBoard;

/**
 * This class describes an AI player of the game.
 * @author uhquw
 * @version 1.0.0
 */
public class AIPlayer extends Player {
    private final AIType aiType;

    /**
     * Constructs an AI player of the game.
     * @param name  the name of the AI
     * @param token the token the AI can place on the game board
     */
    public AIPlayer(final String name, final Hexagon token) {
        super(name, token);
        aiType = AIType.valueOf(name);
    }

    /**
     * Places a token for the AI. If the AI can win next turn, it places the token, so it wins.
     * If the player can win next turn, it places the token, so the player cannot win.
     * If neither applies, based on the AIType the next move is calculated.
     * @param aiGame       the game, where the AI is doing its next move
     * @param playersToken the token of the opposing player
     * @return A confirmation of the successful move, the AI has done
     */
    public final String place(AIGame aiGame, Hexagon playersToken) {
        Hexagon aiToken = getToken();
        GameBoard gameBoard = aiGame.getGameBoard();
        Vector2D aiWinningvector = gameBoard.winInNextMove(aiToken);
        if (aiWinningvector != null) {
            gameBoard.place(aiWinningvector, aiToken);
            return aiGame.update();
        }
        Vector2D playerWinningVector = gameBoard.winInNextMove(playersToken);
        if (playerWinningVector != null) {
            gameBoard.place(playerWinningVector, aiToken);
            return aiGame.update();
        } else {
            return aiType.turn(aiGame);
        }
    }
}
