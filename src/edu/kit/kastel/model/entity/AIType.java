package edu.kit.kastel.model.entity;

import edu.kit.kastel.model.exceptions.PlaceException;
import edu.kit.kastel.model.logic.AIGame;
import edu.kit.kastel.model.logic.GameBoard;
import edu.kit.kastel.ui.ResultType;

/**
 * An enumeration of the different AI players.
 * @author uhquw
 * @version 1.0.2
 */
public enum AIType {
    /**
     * Represents BogoAI and executes its move.
     */
    BogoAI("BogoAI") {
        @Override
        public String turn(AIGame aiGame) {
            Vector2D playersLastMove = aiGame.getLastMove();
            int xPos = playersLastMove.getxPos();
            int yPos = playersLastMove.getyPos();
            if (aiGame.getAllTurns() == 1 && (xPos + yPos) % 2 == 0) {
                return aiGame.swap();
            }
            GameBoard gameBoard = aiGame.getGameBoard();
            Hexagon aiToken = aiGame.getCurrentPlayer().getToken();
            StringBuilder result = new StringBuilder();
            int middle = (gameBoard.getSize() - 1) / 2;
            int distXMiddle = middle - xPos;
            int distYMiddle = middle - yPos;
            int newXCoor = middle + distXMiddle;
            int newYCoor = middle + distYMiddle;
            try {
                gameBoard.place(new Vector2D(newXCoor, newYCoor), aiToken);
            } catch (PlaceException exception) {
                Vector2D nextFreeHexagon = gameBoard.getNextFreeHexagon();
                newXCoor = nextFreeHexagon.getxPos();
                newYCoor = nextFreeHexagon.getyPos();
                gameBoard.place(nextFreeHexagon, aiToken);
            }
            result.append(AI_PLACE_FORMAT.formatted(getName(), newXCoor, newYCoor) + aiGame.update());
            return result.append(aiGame.update()).toString();
        }
    },
    /**
     * Represents HeroAI and executes its move.
     */
    HeroAI("HeroAI") {
        @Override
        public String turn(AIGame aiGame) {
            return null;
        }
    };

    private static final String AI_PLACE_FORMAT = "%s places at %d %d" + ResultType.NEW_LINE_SYMBOL;
    private final String name;
    AIType(final String name) {
        this.name = name;
    }

    /**
     * Gets the name of the AIType.
     * @return the name of the AIType
     */
    protected String getName() {
        return name;
    }

    /**
     * Executes the move of the AI player.
     * @param aiGame the game, where the move is executed
     * @return A confirmation of the successful move
     */
    public abstract String turn(AIGame aiGame);
}
